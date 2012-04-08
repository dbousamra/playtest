package models

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Engine(
    id: Pk[Long] = NotAssigned, 
    position: String, 
    cc: Int, 
    cylinders: Int, 
    engineType: String, 
    valves: Int
    )

object Engine {
    
  val simple = {
    get[Pk[Long]]("engine.id") ~
    get[String]("engine.position") ~
    get[Int]("engine.cc") ~
    get[Int]("engine.cylinders") ~
    get[String]("engine.type") ~
    get[Int]("engine.valves") map {
      case id ~ position ~ cc ~ cylinders ~ engineType ~ valves => Engine(id, position, cc, cylinders, engineType, valves)
    }
  }
}