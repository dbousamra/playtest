package models

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Aspiration(id: Pk[Long] = NotAssigned, aspType: String)

object Aspiration {
    
  /**
   * Parse a Company from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("aspiration.id") ~
    get[String]("aspiration.type") map {
      case id~aspType => Aspiration(id, aspType)
    }
  }
  
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { (implicit connection =>
    SQL("select * from aspiration order by type").as(Aspiration.simple *).map(c => c.id.toString -> c.aspType))
  }
  
}