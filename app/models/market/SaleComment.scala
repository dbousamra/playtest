package models

import play.api.db._
import play.api.Play.current
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import models._

class SaleComment(
    val id: Long,
    val userId: Long, 
    val saleId: Long, 
    val text: String, 
    val accepted: Boolean) {
  def this() = this(1, 1, 1, "", false)
}

object SaleComment extends Schema {

  import models.Sales._
  import models.Users._
  
  val saleComments = table[SaleComment]
  
  def findBySaleId(id: Long): List[(SaleComment, User)] = inTransaction {
    from(saleComments, sales, users)((saleComment, sale, user) => (
      where(saleComment.saleId === sale.id
      and saleComment.userId === user.id
      and sale.id === id)
      select((saleComment, user))
    )).toList
  }
  
  def findByUserId(id: Long): List[(SaleComment, Sale)] = inTransaction {
    from(saleComments, sales, users)((saleComment, sale, user) => (
      where(saleComment.saleId === sale.id
      and saleComment.userId === user.id
      and user.id === id)
      select((saleComment, sale))
    )).toList
  }
  
  def findAll: Seq[SaleComment] = inTransaction {
    from(saleComments)(select(_)).toSeq
  }
}