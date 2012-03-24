package models.market

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Sale(id: Pk[Long] = NotAssigned, modelId: Long, year: Date, price: Int, mileage: Int)

object Sale {
  
  val simple = {
    get[Pk[Long]]("sales.id") ~
      get[Long]("sales.model_id") ~
      get[Date]("sales.year") ~
      get[Int]("sales.price") ~
      get[Int]("sales.mileage") map {
        case id ~ modelId ~ year ~ price ~ mileage => Sale(id, modelId, year, price, mileage)
      }
  }
  
  val withmodelmake = Sale.simple ~ (Model.simple ?) ~ (Make.simple ?) map {
    case sale ~ model ~ make => (sale, model, make)
  }
  
  
  def byId(id: Long): List[(Sale, Option[Model], Option[Make])] = {

    DB.withConnection { implicit connection =>

      val sales = SQL(
        """
          select * from sales 
          left join model on sales.model_id = model.id
          left join make on model.make_id = make.id
          where model_id = {id}
        """).on('id -> id).as(Sale.withmodelmake *)
        
     sales
    }
  }
  
  def list: List[(Sale, Option[Model], Option[Make])] = {

    DB.withConnection { implicit connection =>

      val sales = SQL(
        """
          select * from sales 
          left join model on sales.model_id = model.id
          left join make on model.make_id = make.id
        """).as(Sale.withmodelmake *)
        
     sales
    }
  }
}


