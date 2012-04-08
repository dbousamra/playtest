package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import models._
import anorm._
import anorm.SqlParser._

case class Model(id: Pk[Long] = NotAssigned, name: String, year: Option[Date],  engineId: Option[Long], makeId: Option[Long])

object Model {

  // -- Parsers

  /**
   * Parse a model from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("model.id") ~
      get[String]("model.name") ~
      get[Option[Date]]("model.year") ~
      get[Option[Long]]("model.engine_id") ~
      get[Option[Long]]("model.make_id") map {
        case id ~ name ~ year ~ engineId ~ makeId => Model(id, name, year, engineId, makeId)
      }
  }

  /**
   * Parse a (model,make) from a ResultSet
   */
  val withmakeaspiration = Model.simple ~ (Make.simple ?) ~ (Engine.simple ?) map {
    case model ~ make ~ engine => (model, make, engine)
  }

  // -- Queries

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
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[(Model, Option[Make], Option[Engine])] = {

    println(filter)
    
    val offest = pageSize * page

    DB.withConnection { implicit connection =>

      val models = SQL(
        """
          select * from model 
          left join make on model.make_id = make.id
          left join engine on model.engine_id = engine.id
          where model.name like {filter}
          order by {orderBy} nulls last
          limit {pageSize} offset {offset}
        """).on(
          'pageSize -> pageSize,
          'offset -> offest,
          'filter -> filter,
          'orderBy -> orderBy).as(Model.withmakeaspiration *)

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
          set name = {name}, year = {year}, make_id = {make_id}, engine_id = {engine_id}
          where id = {id}
        """).on(
          'id -> id,
          'name -> model.name,
          'year -> model.year,
          'engine_id -> model.engineId,
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
          'year -> model.year,
          'make_id -> model.makeId,
          'engine_id -> model.engineId)
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