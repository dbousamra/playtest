package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import controllers._
import models._
import controllers.Authentication._
import controllers.Management._
import models._
import java.text.NumberFormat
import java.util.Locale
import java.sql.Blob
import play.api.libs.iteratee.Enumerator

object Market extends Controller {

  val formatter = NumberFormat.getCurrencyInstance(Locale.US)
  formatter.setMaximumFractionDigits(0)

  def listSalesById(id: Long) = UnAuthenticated { implicit request =>
    Ok(html.market.listSales(
      Sale.listSalesById(id), formatter)(new Flash))
  }

  def listSalesByUserId(id: Long) = UnAuthenticated { implicit request =>
    Ok(html.market.listSales(
      Sale.listSalesByUserId(id), formatter)(new Flash))
  }

  def listSales = UnAuthenticated { implicit request =>
    Ok(html.market.listSales(
      Sale.listSales, formatter)(new Flash))
  }

  def showSaleById(id: Long) = UnAuthenticated { implicit request =>
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

  def createSaleForm(user: User) = Form[Sale](
    mapping(
      "id" -> ignored(NotAssigned: Pk[Long]),
      "model" -> longNumber,
      "year" -> date("yyyy"),
      "price" -> number,
      "mileage" -> number) {
        (id, model, year, price, mileage) => Sale(id, user.id.get, model, year, price, mileage)
      } {
        (sale: Sale) => Some(sale.id, sale.modelId, sale.year, sale.price, sale.mileage)
      })

  def save = Authenticated { implicit request =>
    createSaleForm(request.user.get).bindFromRequest.fold(
      formWithErrors => BadRequest(html.market.createSale(formWithErrors)),
      sale => {
        Sale.insert(sale)
        Management.managementHome.flashing("success" -> "Sale %s has been created")
      }
    )
  }

  def sell = WithUser { implicit request =>
    Ok(html.market.createSale(createSaleForm(request.user.get)))
  }
}