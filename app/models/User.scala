package models
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(id: Pk[Long], email: String, name: String, password: String)

object User {

  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("user.id") ~
      get[String]("user.email") ~
      get[String]("user.name") ~
      get[String]("user.password") map {
        case id ~ email ~ name ~ password => User(id, email, name, password)
      }
  }

  // -- Queries

  /**
   * Retrieve a User from email.
   */
  def findByEmail(email: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where email = {email}").on(
        'email -> email).as(User.simple.singleOpt)
    }
  }
  
  def findById(id: Long): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where id = {id}").on(
        'id -> id).as(User.simple.singleOpt)
    }
  }

  /**
   * Retrieve all users.
   */
  def findAll: Seq[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user").as(User.simple *)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(email: String, password: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
         select * from user where 
         email = {email} and password = {password}
        """).on(
          'email -> email,
          'password -> password).as(User.simple.singleOpt)
    }
  }

  /**
   * Create a User.
   */
  def create(user: User): User = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into user values (
          {id}, {email}, {name}, {password}
          )
        """).on(
          'id -> user.id,
          'email -> user.email,
          'name -> user.name,
          'password -> user.password).executeUpdate()

      user

    }
  }

  def update(id: Long, user: User) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          update user
          set email = {email}, name = {name}, password = {password}
          where id = {id}
        """).on(
          'id -> id,
          'email -> user.email,
          'name -> user.name,
          'password -> user.password)
          .executeUpdate()
    }
  }
}