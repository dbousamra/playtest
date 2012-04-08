package models

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Engine(id: Pk[Long] = NotAssigned, aspirationId: Long, position: String, cc: Int, cylinders: Int, engineType: String, valves: Int)

object Engine {
    
  /**
   * Parse a Company from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("engine.id") ~
    get[Long]("engine.aspiration_id") ~
    get[String]("engine.position") ~
    get[Int]("engine.cc") ~
    get[Int]("engine.cylinders") ~
    get[String]("engine.type") ~
    get[Int]("engine.valves") map {
      case id ~ aspirationId ~ position ~ cc ~ cylinders ~ engineType ~ valves => Engine(id, aspirationId, position, cc, cylinders, engineType, valves)
    }
  }
}