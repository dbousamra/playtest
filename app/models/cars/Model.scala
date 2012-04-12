package models

import java.sql.{ Date }
import org.squeryl.dsl.QueryDsl
import play.api.db._
import play.api.Play.current
import models._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column

case class ModelUnified(
  id: Long,
  name: String,
  year: Option[Date],
  trim: Option[String],
  seats: Option[Int],
  doors: Option[Int],
  body: Option[String],
  make: Make,
  engine: Engine)

case class Model(
  val id: Long,
  val makeId: Long,
  val name: String,

  val year: Option[Date],
  val trim: Option[String],
  val seats: Option[Int],
  val doors: Option[Int],
  val body: Option[String],

  val position: Option[String],
  val cc: Option[Int],
  val cylinders: Option[Int],
  val engine_type: Option[String],
  val valves: Option[Int] //
  //  val drivetype: Option[String],
  //  val transmission: Option[String],
  //
  //  val weight: Option[Int],
  //  val length: Option[Int],
  //  val width: Option[Int],
  //  val height: Option[Int],
  //  val wheelbase: Option[Int],
  //
  //  val highway: Option[Int],
  //  val mixed: Option[Int],
  //  val city: Option[Int],
  //  val tank_size: Option[Int],
  //
  //  val power: Option[Int],
  //  val power_rpm: Option[Int],
  //  val torque: Option[Int],
  //  val torque_rpm: Option[Int]) {
  ) {
  def this() = this(0, 0, "", Some(Date.valueOf("2004-01-01")), Some(""), Some(0), Some(0), Some(""), Some(""), Some(0), Some(0), Some(""), Some(0))
}

object Models extends Schema {

  import Makes._

  val models = table[Model]

  implicit def modelUnified2Model(model: ModelUnified): Model = {
    Model(model.id, model.make.id, model.name, model.year, model.trim, model.seats, model.doors, model.body, 
        model.position, model.cc, model.cylinders, model.engine_type, model.valves
        
    )
  }

  implicit def model2ModelUnified(model: Model): ModelUnified = {
    ModelUnified(model.id, model.name, model.year, model.trim, model.seats, model.doors, model.body, Makes.findById(model.makeId),
      Engine(model.position, model.cc, model.cylinders, model.engine_type, model.valves))
  }

  def asModelUnified(model: Model, make: Make): ModelUnified = {
    ModelUnified(model.id, model.name, model.year, model.trim, model.seats, model.doors, model.body,
      make,
      Engine(model.position, model.cc, model.cylinders, model.engine_type, model.valves))
  }

  def findById(id: Long) = inTransaction {
    from(models, makes)((model, make) => (
      where(model.makeId === make.id
        and model.id === id)
      select (asModelUnified(model, make)))).headOption
  }

  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[ModelUnified] = inTransaction {

    val pageSize = 10
    val offset = pageSize * page

    val mods =
      from(models, makes)((model, make) => (
        where((model.makeId === make.id) and (model.name like filter))
        select (asModelUnified(model, make))
        orderBy (orderBy))).page(offset, pageSize).toList

    val totalRows =
      from(models, makes)((model, make) => (
        where((model.makeId === make.id) and (model.name like filter))
        compute (count)))

    Page(mods, page, offset, totalRows)
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