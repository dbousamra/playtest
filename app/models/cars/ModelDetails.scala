package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import org.squeryl.KeyedEntity

case class ModelDetail(
  val id: Long = 0,

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
  val torque_rpm: Option[Int]) extends KeyedEntity[Long] {

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

  on(modelDetails)(modelDetail => declare(
    modelDetail.id is (autoIncremented),
    modelDetail.id is (unique)))

  implicit def modelDetail2Unified(modelDetail: ModelDetail): ModelDetailUnified = {
    ModelDetailUnified(
      modelDetail.id,
      Engine(modelDetail.position, modelDetail.cc, modelDetail.cylinders, modelDetail.engine_type, modelDetail.valves),
      Drivetrain(modelDetail.drivetype, modelDetail.transmission),
      Dimensions(modelDetail.weight, modelDetail.length, modelDetail.width, modelDetail.height, modelDetail.wheelbase),
      Economy(modelDetail.highway, modelDetail.mixed, modelDetail.city, modelDetail.tank_size),
      Performance(modelDetail.power, modelDetail.power_rpm, modelDetail.torque, modelDetail.torque_rpm))
  }

  implicit def unified2ModelDetailed(unified: ModelDetailUnified): ModelDetail = {
    ModelDetail(
      unified.id,
      unified.engine.position, unified.engine.cc, unified.engine.cylinders, unified.engine.engine_type, unified.engine.valves,
      unified.drivetrain.drivetype, unified.drivetrain.transmission,
      unified.dimensions.weight, unified.dimensions.length, unified.dimensions.width, unified.dimensions.height, unified.dimensions.wheelbase,
      unified.economy.highway, unified.economy.mixed, unified.economy.city, unified.economy.tank_size,
      unified.performance.power, unified.performance.power_rpm, unified.performance.torque, unified.performance.torque_rpm)
  }

  def findById(id: Long): ModelDetailUnified = inTransaction {
    from(modelDetails)(m =>
      where(m.id === id)
        select (m)).head
  }

  def insert(model: ModelDetail): Long = inTransaction {
    val id = modelDetails.insert(model)
    id.id
  }
}

case class ModelDetailUnified(
  val id: Long = 0,
  val engine: Engine,
  val drivetrain: Drivetrain,
  val dimensions: Dimensions,
  val economy: Economy,
  val performance: Performance)

case class Engine(
  val position: Option[String],
  val cc: Option[Int],
  val cylinders: Option[Int],
  val engine_type: Option[String],
  val valves: Option[Int])

case class Drivetrain(
  val drivetype: Option[String],
  val transmission: Option[String])

case class Dimensions(
  val weight: Option[Int],
  val length: Option[Int],
  val width: Option[Int],
  val height: Option[Int],
  val wheelbase: Option[Int])

case class Economy(
  val highway: Option[Int],
  val mixed: Option[Int],
  val city: Option[Int],
  val tank_size: Option[Int])

case class Performance(
  val power: Option[Int],
  val power_rpm: Option[Int],
  val torque: Option[Int],
  val torque_rpm: Option[Int])

