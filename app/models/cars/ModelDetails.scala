package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column

class ModelDetail(
  val id: Long,

  val position: Option[String],
  val cc: Option[Int],
  val cylinders: Option[Int],
  val engine_type: Option[String],
  val valves: Option[Int],

  val drivetype: Option[String],
  val transmission: Option[String],

  val weight: Option[Int],
  val length: Option[Int],
  val width: Option[Int],
  val height: Option[Int],
  val wheelbase: Option[Int],

  val highway: Option[Int],
  val mixed: Option[Int],
  val city: Option[Int],
  val tank_size: Option[Int],

  val power: Option[Int],
  val power_rpm: Option[Int],
  val torque: Option[Int],
  val torque_rpm: Option[Int]) {

  def this() = this(
    0,
    Some(""), Some(0), Some(0), Some(""), Some(0),
    Some(""), Some(""),
    Some(0), Some(0), Some(0), Some(0), Some(0),
    Some(0), Some(0), Some(0), Some(0),
    Some(0), Some(0), Some(0), Some(0))
}

object ModelDetails extends Schema {

  val modelDetails = table[ModelDetail]

  def findById(id: Long): ModelDetail = inTransaction {
    from(modelDetails)(m =>
      where(m.id === id)
        select (m)).head
  }
}

