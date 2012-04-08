package models

import java.util.{ Date }

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Performance(
  id: Pk[Long] = NotAssigned,
  power: Option[Int],
  powerRpm: Option[Int],
  torque: Option[Int],
  torqueRpm: Option[Int])

object Performance {

  val simple = {
    get[Pk[Long]]("performance.id") ~
      get[Option[Int]]("performance.power") ~
      get[Option[Int]]("performance.power_rpm") ~
      get[Option[Int]]("performance.torque") ~
      get[Option[Int]]("performance.torque_rpm") map {
        case id ~ power ~ powerRpm ~ torque ~ torqueRpm => Performance(id, power, powerRpm, torque, torqueRpm)
      }
  }
}