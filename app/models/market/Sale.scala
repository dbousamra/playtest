package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Sale(id: Pk[Long] = NotAssigned, userId: Long, modelId: Long, year: Date, price: Int, mileage: Int)

object Sale {

  val simple = {
    get[Pk[Long]]("sale.id") ~
      get[Long]("sale.user_id") ~
      get[Long]("sale.model_id") ~
      get[Date]("sale.year") ~
      get[Int]("sale.price") ~
      get[Int]("sale.mileage") map {
        case id ~ userId ~ modelId ~ year ~ price ~ mileage => Sale(id, userId, modelId, year, price, mileage)
      }
  }

  val withModelMake = Sale.simple ~ (Model.simple ?) ~ (Make.simple ?) map {
    case sale ~ model ~ make => (sale, model, make)
  }

  val withImageModelMake = Sale.simple ~ (Image.simple ?) ~ (Model.simple ?) ~ (Make.simple ?) map {
    case sale ~ image ~ model ~ make => (sale, image, model, make)
  }

  val withModelMakeUser = Sale.simple ~ (Model.simple ?) ~ (Make.simple ?) ~ (User.simple ?) map {
    case sale ~ model ~ make ~ user => (sale, model, make, user)
  }

  def listSalesById(id: Long): List[(Sale, Option[Model], Option[Make])] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from sale 
          left join model on sale.model_id = model.id
          left join make on model.make_id = make.id
          where model_id = {id}
        """).on('id -> id).as(Sale.withModelMake *)
    }
  }

  def listSales: List[(Sale, Option[Model], Option[Make])] = {

    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from sale 
          left join model on sale.model_id = model.id
          left join make on model.make_id = make.id
        """).as(Sale.withModelMake *)
    }
  }

  def listSalesByUserId(id: Long): List[(Sale, Option[Model], Option[Make])] = {

    DB.withConnection({ implicit connection =>
      SQL(
        """
          select * from sale
          left join model on sale.model_id = model.id
          left join make on model.make_id = make.id
          left join user on sale.user_id = user.id
          where user.id = {id}
          """).on('id -> id).as(withModelMake *)
    })

  }

  def showSaleById(id: Long): (Sale, Option[Image], Option[Model], Option[Make]) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from sale 
          left join model on sale.model_id = model.id
          left join make on model.make_id = make.id
          left join image on sale.image_id = image.id
          where sale.id = {id}
        """).on('id -> id).as(Sale.withImageModelMake.single)
    }
  }

  /**
   * Create a User.
   */
  def insert(sale: Sale): Sale = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into sale values (
          {id}, {user_id}, {model_id}, {year}, {price}, {mileage}
          )
        """).on(
          'id -> sale.id,
          'user_id -> sale.userId,
          'model_id -> sale.modelId,
          'year -> sale.year,
          'price -> sale.price,
          'mileage -> sale.mileage).executeUpdate()
    }
    sale
  }

}


