package models

import play.api._
import play.api.mvc._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import models._
import java.sql.Blob
import play.mvc.Action

class Image(
  val id: Long,
  val data: Array[Byte]) {
  def this() = this(1, Array[Byte]())
}

object Images extends Schema {

  import Sales._

  val images = table[Image]

  def getImageBySaleId(id: Long): Option[Image] = inTransaction {
    from(images, sales)((image, sale) => (
      where(image.id === sale.imageId
        and sale.id === id)
      select (image))).headOption
  }

}