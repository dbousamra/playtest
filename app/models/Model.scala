package models

import java.util.{ Date }

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Model(id: Pk[Long] = NotAssigned, name: String, introduced: Option[Date], discontinued: Option[Date], aspirationId: Option[Long], makeId: Option[Long])

object Model {

  // -- Parsers

  /**
   * Parse a model from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("model.id") ~
      get[String]("model.name") ~
      get[Option[Date]]("model.introduced") ~
      get[Option[Date]]("model.discontinued") ~
      get[Option[Long]]("model.aspiration_id") ~
      get[Option[Long]]("model.make_id") map {
        case id ~ name ~ introduced ~ discontinued ~ aspirationId ~ makeId => Model(id, name, introduced, discontinued, aspirationId, makeId)
      }
  }

  /**
   * Parse a (model,make) from a ResultSet
   */
  val withmakeaspiration = Model.simple ~ (Make.simple ?) ~ (Aspiration.simple ?) map {
    case model ~ make ~ aspiration => (model, make, aspiration)
  }

  // -- Queries

  /**
   * Retrieve a model from the id.
   */
  def findById(id: Long): Option[Model] = {
    DB.withConnection {
      (implicit connection =>
        SQL("select * from model where id = {id}").on('id -> id).as(Model.simple.singleOpt))
    }
  }

  /**
   * Return a page of (model,make).
   *
   * @param page Page to display
   * @param pageSize Number of models per page
   * @param orderBy model property used for sorting
   * @param filter Filter applied on the name column
   */
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[(Model, Option[Make], Option[Aspiration])] = {

    val offest = pageSize * page

    DB.withConnection { implicit connection =>

      val models = SQL(
        """
          select * from model 
          left join make on model.make_id = make.id
          left join aspiration on model.aspiration_id = aspiration.id
          where model.name like {filter}
          order by {orderBy} nulls last
          limit {pageSize} offset {offset}
        """).on(
          'pageSize -> pageSize,
          'offset -> offest,
          'filter -> filter,
          'orderBy -> orderBy).as(Model.withmakeaspiration *)
          
          println(orderBy)
      val totalRows = SQL(
        """
          select count(*) from model 
          left join make on model.make_id = make.id
          where model.name like {filter}
        """).on(
          'filter -> filter).as(scalar[Long].single)

      Page(models, page, offest, totalRows)

    }
  }

  /**
   * Update a model.
   *
   * @param id The model id
   * @param model The model values.
   */
  def update(id: Long, model: Model) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          update model
          set name = {name}, introduced = {introduced}, discontinued = {discontinued}, make_id = {make_id}, aspiration_id = {aspiration_id}
          where id = {id}
        """).on(
          'id -> id,
          'name -> model.name,
          'introduced -> model.introduced,
          'discontinued -> model.discontinued,
          'aspiration_id -> model.aspirationId,
          'make_id -> model.makeId).executeUpdate()
    }
  }

  /**
   * Insert a new model.
   *
   * @param model The model values.
   */
  def insert(model: Model) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into model values (
            (select next value for model_seq), 
            {name}, {introduced}, {discontinued}, {make_id}, {aspiration_id}
          )
        """).on(
          'name -> model.name,
          'introduced -> model.introduced,
          'discontinued -> model.discontinued,
          'make_id -> model.makeId,
          'aspiration_id -> model.aspirationId)
          .executeUpdate()
    }
  }

  /**
   * Delete a model.
   *
   * @param id Id of the model to delete.
   */
  def delete(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("delete from model where id = {id}").on('id -> id).executeUpdate()
    }
  }
}