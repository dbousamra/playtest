package models
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import models.market._

case class SaleComment(id: Pk[Long], user_id: Long, sale_id: Long, text: String, accepted: Boolean)

object SaleComment {

  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("sale_comment.id") ~
      get[Long]("sale_comment.user_id") ~
      get[Long]("sale_comment.sale_id") ~
      get[String]("sale_comment.text") ~
      get[Boolean]("sale_comment.accepted") map {
        case id ~ user_id ~ sale_id ~ text ~ accepted => SaleComment(id, user_id, sale_id, text, accepted)
      }
  }
  
  val withUser = simple ~ (User.simple) map {
    case saleComment ~ user => (saleComment, user)
  }
  
  val withSale = simple ~ (Sale.simple) map {
    case saleComment ~ sale => (saleComment, sale)
  }
  
  /**
   * Retrieve a SaleComment from email.
   */
  def findBySaleId(id: Long): List[(SaleComment, User)] = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from sale_comment
          left join sale on sale_comment.sale_id = sale.id
          left join user on sale_comment.user_id = user.id
          where sale.id = {id}
          """).on(
        'id -> id).as(SaleComment.withUser *)
    }
  }
  
  def findByUserId(id: Long): List[(SaleComment, Sale)] = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from sale_comment
          left join sale on sale_comment.sale_id = sale.id
          left join user on sale_comment.user_id = user.id
          where user.id = {id}
          """).on(
        'id -> id).as(SaleComment.withSale *)
    }
  }

  /**
   * Retrieve all users.
   */
  def findAll: Seq[SaleComment] = {
    DB.withConnection { implicit connection =>
      SQL("select * from sale_comment").as(SaleComment.simple *)
    }
  }
}