package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Dimensions(
  id: Pk[Long] = NotAssigned,
  weight: Option[Int],
  length: Option[Int],
  width: Option[Int],
  height: Option[Int],
  wheelbase: Option[Int])

object Dimensions {

  val simple = {
    get[Pk[Long]]("dimensions.id") ~
      get[Option[Int]]("dimensions.weight") ~
      get[Option[Int]]("dimensions.length") ~
      get[Option[Int]]("dimensions.width") ~
      get[Option[Int]]("dimensions.height") ~
      get[Option[Int]]("dimensions.wheelbase") map {
        case id ~ weight ~ length ~ width ~ height ~ wheelbase => Dimensions(id, weight, length, width, height, wheelbase)
      }
  }
}