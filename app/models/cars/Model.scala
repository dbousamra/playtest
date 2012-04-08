package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import models._
import anorm._
import anorm.SqlParser._

case class ModelUnified(
  id: Pk[Long] = NotAssigned,
  name: String,
  year: Option[Date],
  trim: Option[String],
  seats: Option[Int],
  doors: Option[Int],
  body: Option[String],
  make: Make,
  engine: Engine,
  driveTrain: Drivetrain,
  dimensions: Dimensions,
  economy: Economy,
  performance: Performance)
  

case class Model(
  id: Pk[Long] = NotAssigned,
  name: String,
  year: Option[Date],
  trim: Option[String],
  seats: Option[Int],
  doors: Option[Int],
  body: Option[String],
  makeId: Long,
  engineId: Long,
  drivetrainId: Long,
  dimensionsId: Long,
  economyId: Long,
  performanceId: Long)

object Model {
  /**
   * Parse a model from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("model.id") ~
      get[String]("model.name") ~
      get[Option[Date]]("model.year") ~
      get[Option[String]]("model.trim") ~
      get[Option[Int]]("model.seats") ~
      get[Option[Int]]("model.doors") ~
      get[Option[String]]("model.body") ~
      get[Long]("model.make_id") ~
      get[Long]("model.engine_id") ~
      get[Long]("model.drivetrain_id") ~
      get[Long]("model.dimensions_id") ~
      get[Long]("model.economy_id") ~
      get[Long]("model.performance_id") map {
        case id ~ name ~ year ~ trim ~ seats ~ doors ~ body ~ makeId ~ engineId ~ drivetrainId ~ dimensionsId ~ economyId ~ performanceId =>
          Model(id, name, year, trim, seats, doors, body, makeId, engineId, drivetrainId, dimensionsId, economyId, performanceId)
      }
  }

  val unified = Model.simple ~ (Make.simple) ~ (Engine.simple) ~ (Drivetrain.simple) ~ (Dimensions.simple) ~ (Economy.simple) ~ (Performance.simple) map {
    case model ~ make ~ engine ~ drivetrain ~ dimensions ~ economy ~ performance => ModelUnified(
      model.id,
      model.name,
      model.year,
      model.trim,
      model.seats,
      model.doors,
      model.body,
      make,
      engine,
      drivetrain,
      dimensions,
      economy,
      performance)
  }

  // -- Queries

  def findById(id: Long): Option[ModelUnified] = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from model 
          
          left join make on model.make_id = make.id
          left join engine on model.engine_id = engine.id
          left join drivetrain on model.drivetrain_id = drivetrain.id
          left join dimensions on model.dimensions_id = dimensions.id
          left join economy on model.economy_id = economy.id
          left join performance on model.performance_id = performance.id
          
          where model.id = {id}""").on('id -> id).as(Model.unified.singleOpt)

    }

  }

  /**
   * Return a page of (model unified).
   *
   * @param page Page to display
   * @param pageSize Number of models per page
   * @param orderBy model property used for sorting
   * @param filter Filter applied on the name column
   */
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[ModelUnified] = {

    println(filter)

    val offest = pageSize * page

    DB.withConnection { implicit connection =>

      val models = SQL(
        """
          select * from model 
          
          left join make on model.make_id = make.id
          left join engine on model.engine_id = engine.id
          left join drivetrain on model.drivetrain_id = drivetrain.id
          left join dimensions on model.engine_id = dimensions.id
          left join economy on model.economy_id = economy.id
          left join performance on model.performance_id = performance.id
          
          where model.name like {filter}
          order by {orderBy} nulls last
          limit {pageSize} offset {offset}
        """).on(
          'pageSize -> pageSize,
          'offset -> offest,
          'filter -> filter,
          'orderBy -> orderBy).as(Model.unified *)

      val totalRows = SQL(
        """
          select count(*) from model 
          
          left join make on model.make_id = make.id
          left join engine on model.engine_id = engine.id
          left join drivetrain on model.drivetrain_id = drivetrain.id
          left join dimensions on model.engine_id = dimensions.id
          left join economy on model.economy_id = economy.id
          left join performance on model.performance_id = performance.id
          
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