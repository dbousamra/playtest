package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Economy(id: Pk[Long] = NotAssigned, 
    highway: Option[Int], 
    mixed: Option[Int],
    city: Option[Int],
    tankSize: Option[Int])

object Economy {
   
  val simple = {
    get[Pk[Long]]("economy.id") ~
    get[Option[Int]]("economy.highway") ~
    get[Option[Int]]("economy.mixed") ~
    get[Option[Int]]("economy.city") ~
    get[Option[Int]]("economy.tank_size") map {
      case id ~ highway ~ mixed ~ city ~ tankSize => Economy(id, highway, mixed, city, tankSize)
    }
  }
}