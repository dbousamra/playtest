package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Drivetrain(id: Pk[Long] = NotAssigned, drivetype: String, transmission: String)

object Drivetrain {
   
  val simple = {
    get[Pk[Long]]("drivetrain.id") ~
    get[String]("drivetrain.drivetype") ~
    get[String]("drivetrain.transmission") map {
      case id ~ drivetype ~ transmission => Drivetrain(id, drivetype, transmission)
    }
  }
}