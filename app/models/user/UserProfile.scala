package models
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class UserProfile(
    firstName: String, 
    lastName: String, 
    phone: String,
    country: Long,
    state: Long
    )

object UserProfile {
  
  // -- Parsers
  
  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[String]("user.email") ~
    get[String]("user.name") ~
    get[String]("user.password") map {
      case email~name~password => User(email, name, password)
    }
  }
  

}