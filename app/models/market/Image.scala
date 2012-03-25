package models.market;

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._
import anorm.Column
import java.sql.Blob


case class Image(id: Pk[Long] = NotAssigned, data: Blob)

object Image {
  
   implicit def rowToBlob: Column[Blob] = Column.nonNull { (value, meta) =>
      val MetaDataItem(qualified, nullable, clazz) = meta
      value match {
        case blob: Blob => Right(blob)
        case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Blob for column " + qualified))
      }
    }
  
   val simple = {
    get[Pk[Long]]("image.id") ~
      get[Blob]("image.data") map {
        case id ~ data => Image(id, data)
      }
   }
   
   def showImageById(id: Long): Option[Image] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from image
          where image.id = {id}
        """).on('id -> id).as(Image.simple.singleOpt)
    }
  }
	
}