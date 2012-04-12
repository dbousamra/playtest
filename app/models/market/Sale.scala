package models

import java.sql.{ Date }
import play.api.db._
import play.api.Play.current
import models._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column

class Sale(
  val id: Long,
  val userId: Long,
  val modelId: Long,
  val imageId: Long,
  val year: Date,
  val price: Int,
  val mileage: Int) {
  def this() = this(1, 1, 1, 1, Date.valueOf("2011-01-01"), 1000, 10000);
}

object Sales extends Schema {

  import Models._
  import Makes._
  import Images._

  val sales = table[Sale]

  def listSales: List[(Sale, Option[Model], Option[Make])] = inTransaction {
    from(sales, models, makes)((sale, model, make) => (
      where(model.makeId === make.id
      and sale.modelId === model.id)
      select((sale, Some(model), Some(make)))
    )).toList
  }

  def listSalesByUserId(id: Long): List[(Sale, Option[Model], Option[Make])] = inTransaction {
    val x =from(sales, models, makes)((sale, model, make) => (
      where(model.makeId === make.id
            and sale.modelId === model.id
            and sale.userId === id)
      select((sale, Some(model), Some(make)))
    ))
    x.toList

  }
  
  def getSaleById(id: Long): (Sale, Option[ModelUnified]) = inTransaction {
    val sale = from(sales)((sale) => (
      where(sale.id === id)
      select(sale)
    )).head
    val model = Models.findById(sale.modelId)
    (sale, model)
  }
  
  def insert(sale: Sale) = inTransaction {
    sales.insert(sale)
  }

}


