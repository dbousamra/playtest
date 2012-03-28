package models.cars

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Make(id: Pk[Long] = NotAssigned, name: String)

object Make {
    
  /**
   * Parse a Company from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("make.id") ~
    get[String]("make.name") map {
      case id~name => Make(id, name)
    }
  }
  
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { (implicit connection =>
    SQL("select * from make order by name").as(Make.simple *).map(c => c.id.toString -> c.name))
  }
  
}

