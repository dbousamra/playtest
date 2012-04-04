package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import models._
import controllers.Authentication._
import models.market._
import java.text.NumberFormat
import java.util.Locale
import java.sql.Blob
import play.api.libs.iteratee.Enumerator

object Market extends Controller {

  val formatter = NumberFormat.getCurrencyInstance(Locale.US)
  formatter.setMaximumFractionDigits(0)

  def listSalesById(id: Long) = Authenticated { implicit request =>
    Ok(html.market.listSales(
      Sale.listSalesById(id), formatter)(new Flash))
  }
  
  def listSalesByUserId(id: Long) = Authenticated { implicit request =>
    Ok(html.market.listSales(
      Sale.listSalesByUserId(id), formatter)(new Flash))
  }

  def listSales = Authenticated { implicit request =>
    Ok(html.market.listSales(
      Sale.listSales, formatter)(new Flash))
  }

  def showSaleById(id: Long) = Authenticated { implicit request =>
    Ok(html.market.showSale(
      Sale.showSaleById(id),
      SaleComment.findBySaleId(id),
      formatter)(new Flash))
  }

  def getImageById(id: Long) = Action {
    val image = Image.showImageById(id).get.data
    val binary = image.getBytes(1, image.length().asInstanceOf[Int])
    Ok(binary).as("image/jpeg")

  }

  def cars = Authenticated { implicit request =>
    Ok("Hello " + request.user)
  }
}