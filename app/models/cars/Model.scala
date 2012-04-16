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

  def update(id: Long, newModel: Model) = inTransaction {
    models.update(m =>
      where(m.id === id) set (
        m.name := newModel.name,
        m.year := newModel.year,
        m.trim := newModel.trim,
        m.seats := newModel.seats,
        m.doors := newModel.doors,
        m.body := newModel.body,
        m.makeId := newModel.makeId))
  }

  def insert(model: Model) = inTransaction {
    models.insert(model)
  }

  def delete(id: Long) = inTransaction {
    models.deleteWhere(m => m.id === id)
  }
}