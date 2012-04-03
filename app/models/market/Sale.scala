package models.market

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models.cars._
import models.User

case class Sale(id: Pk[Long] = NotAssigned, userId: Long, modelId: Long, year: Date, price: Int, mileage: Int)

object Sale {
  
  val simple = {
    get[Pk[Long]]("sales.id") ~
      get[Long]("sales.user_id") ~
      get[Long]("sales.model_id") ~
      get[Date]("sales.year") ~
      get[Int]("sales.price") ~
      get[Int]("sales.mileage") map {
        case id ~ userId~ modelId ~ year ~ price ~ mileage => Sale(id, userId, modelId, year, price, mileage)
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
          select * from sales 
          left join model on sales.model_id = model.id
          left join make on model.make_id = make.id
          where model_id = {id}
        """).on('id -> id).as(Sale.withModelMake *)
    }
  }
  
  def listSales: List[(Sale, Option[Model], Option[Make])] = {

    DB.withConnection { implicit connection =>   	
      SQL(
        """
          select * from sales 
          left join model on sales.model_id = model.id
          left join make on model.make_id = make.id
        """).as(Sale.withModelMake *) 
    }
  }
  
  def listSalesByUserId(id: Long): List[(Sale, Option[Model], Option[Make])] = {
    
    DB.withConnection( { implicit connection =>
      SQL (
    	  """
          select * from sales
          left join model on sales.model_id = model.id
          left join make on model.make_id = make.id
          left join user on sales.user_id = user.id
          where user.id = {id}
          """
          
          ).on('id -> id).as(withModelMake *)
    })
    
  }
  
  def showSaleById(id: Long): (Sale, Option[Image], Option[Model], Option[Make]) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from sales 
          left join model on sales.model_id = model.id
          left join make on model.make_id = make.id
          left join image on sales.image_id = image.id
          where sales.id = {id}
        """).on('id -> id).as(Sale.withImageModelMake.single)
    }
  }  
}


