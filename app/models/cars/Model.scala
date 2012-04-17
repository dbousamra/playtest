package models

import java.sql.{ Date }
import org.squeryl.dsl.QueryDsl
import play.api.db._
import play.api.Play.current
import models._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import org.squeryl.KeyedEntity

case class ModelUnified(
  id: Long,
  name: String,
  year: Option[Date],
  trim: Option[String],
  seats: Option[Int],
  doors: Option[Int],
  body: Option[String],
  make: Make,
  details: ModelDetailUnified
  )

case class Model(
  val id: Long = 0,
  val makeId: Long,
  val modelDetailId: Long,
  val name: String,

  val year: Option[Date],
  val trim: Option[String],
  val seats: Option[Int],
  val doors: Option[Int],
  val body: Option[String]) extends KeyedEntity[Long]  {
  def this() = this(0, 0, 0, "", Some(Date.valueOf("2004-01-01")), Some(""), Some(0), Some(0), Some("")) 
}

object Models extends Schema {

  import Makes._
  import ModelDetails._

  val models = table[Model]
  
  on(models)(model => declare(
      model.id is (autoIncremented),
      model.id is (unique)
    ))


  implicit def modelUnified2Model(modelUnified: ModelUnified): Model = {
    Model(modelUnified.id, modelUnified.make.id, modelUnified.details.id, modelUnified.name, modelUnified.year, modelUnified.trim, modelUnified.seats, modelUnified.doors, modelUnified.body)
  }

  implicit def model2ModelUnified(model: Model): ModelUnified = {
    ModelUnified(model.id, model.name, model.year, model.trim, model.seats, model.doors, model.body, 
        Makes.findById(model.makeId), 
        ModelDetails.findById(model.modelDetailId) 
    )
  }

  def asModelUnified(model: Model, make: Make, modelDetail: ModelDetail): ModelUnified = {
    ModelUnified(model.id, model.name, model.year, model.trim, model.seats, model.doors, model.body,
      make, modelDetail)
  }

  def findById(id: Long) = inTransaction {
    from(models, makes, modelDetails)((model, make, modelDetail) => (
      where(model.makeId === make.id
        and model.id === id
        and model.modelDetailId === modelDetail.id)
      select (asModelUnified(model, make, modelDetail)))).headOption
  }

  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[ModelUnified] = inTransaction {

    val pageSize = 10
    val offset = pageSize * page

    val mods =
      from(models, makes, modelDetails)((model, make, modelDetail) => (
        where((model.makeId === make.id) 
            and (model.name like filter)
            and (model.modelDetailId === modelDetail.id))
        select (asModelUnified(model, make, modelDetail))
        orderBy (orderBy))).page(offset, pageSize)

    val totalRows =
      from(models, makes, modelDetails)((model, make, modelDetail) => (
        where((model.makeId === make.id) and (model.name like filter) and (model.modelDetailId === modelDetail.id))
        compute (count)))

    Page(mods.toList, page, offset, totalRows)
  }

  def update(id: Long, newModel: ModelUnified) = inTransaction {
    println("Model id = " + newModel.modelDetailId)
    models.update(m =>
      where(m.id === id) set (
        m.name := newModel.name,
        m.year := newModel.year,
        m.trim := newModel.trim,
        m.seats := newModel.seats,
        m.doors := newModel.doors,
        m.body := newModel.body,
        m.makeId := newModel.makeId,
        m.modelDetailId := newModel.modelDetailId)    
    )
    
    
    modelDetails.update(m => 
      where(m.id === id) set (
          
        m.position := newModel.details.engine.position,
        m.cylinders := newModel.details.engine.cylinders,
        m.engine_type := newModel.details.engine.engine_type,
        m.valves := newModel.details.engine.valves,
        
        m.drivetype := newModel.details.drivetrain.drivetype,
        m.transmission:= newModel.details.drivetrain.transmission,
        
        m.weight := newModel.details.dimensions.weight,
        m.length := newModel.details.dimensions.length,
        m.width := newModel.details.dimensions.width,
        m.height := newModel.details.dimensions.height,
        m.wheelbase := newModel.details.dimensions.wheelbase,
        
        m.highway := newModel.details.economy.highway,
        m.mixed := newModel.details.economy.mixed,
        m.city := newModel.details.economy.city,
        m.tank_size := newModel.details.economy.tank_size,
         
        m.power := newModel.details.performance.power,
        m.power_rpm := newModel.details.performance.power_rpm,
        m.torque := newModel.details.performance.torque,
        m.torque_rpm := newModel.details.performance.torque_rpm) 
     )  
  }

  def insert(model: ModelUnified) = inTransaction {
    val id = ModelDetails.insert(model.details)
    val newModel = Model(model.id, model.makeId, id, model.name, model.year, model.trim, model.seats, model.doors, model.body)
    models.insert(newModel)
  }

  def delete(id: Long) = inTransaction {
    models.deleteWhere(m => m.id === id)
  }
}