package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import models._
import models.market._
import java.text.NumberFormat
import java.util.Locale
import java.sql.Blob
import play.api.libs.iteratee.Enumerator

object Market extends Controller {

  val formatter = NumberFormat.getCurrencyInstance(Locale.US)
  formatter.setMaximumFractionDigits(0)

  def listSalesById(id: Long) = Action { implicit request =>
    Ok(html.listSales(
      Sale.listSalesById(id), formatter))
  }

  def listSales = Action { implicit request =>
    Ok(html.listSales(
      Sale.listSales, formatter))
  }

  def showSaleById(id: Long) = Action { implicit request =>
    Ok(html.showSale(
      Sale.showSaleById(id), formatter))
  }

  def showImageById(id: Long) = Action { implicit request =>
    Ok(html.showImages(
      Image.showImageById(id)))
  }
  
  
  
  def getImageById(id: Long) = Action {
    val image = Image.showImageById(id).get.data
    val binary = image.getBytes(1, image.length().asInstanceOf[Int])
    Ok(binary).as("image/jpeg")
    
  }
}