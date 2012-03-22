package models.market

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Sale(id: Pk[Long] = NotAssigned, modelId: Option[Long], price: Option[Int])

object Sale {
  
  val simple = {
    get[Pk[Long]]("sales.id") ~
      get[Option[Long]]("sales.model_id") ~
      get[Option[Int]]("sales.price") map {
        case id ~ modelId ~ price => Sale(id, modelId, price)
      }
  }
  
  val withmodelmake = Sale.simple ~ (Model.simple ?) map {
    case sale ~ model => (sale, model)
  }
  
  
  def byId(id: Long): List[(Sale, Option[Model])] = {

    DB.withConnection { implicit connection =>

      val sales = SQL(
        """
          select * from sales 
          left join model on sales.model_id = model.id
          where model_id = {id}
        """).on('id -> id).as(Sale.withmodelmake *)
        
     sales
    }
  }
  
  def list: List[(Sale, Option[Model])] = {

    DB.withConnection { implicit connection =>

      val sales = SQL(
        """
          select * from sales 
          left join model on sales.model_id = model.id
        """).as(Sale.withmodelmake *)
        
     sales
    }
  }
}


