package controllers

import play.api._
import controllers.Authentication._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import controllers._
import views._
import models._
import java.text.NumberFormat
import java.util.Locale

/**
 * Manage a database of models
 */
object Application extends Controller {

  def index = UnAuthenticated { implicit request =>
//    val y = Sale.listSalesFilter().filter {
//      case (sale, model, make) => sale.price > 11000
//    }
//    val z = y.filter {
//      case (sale, model, make) => sale.mileage < 95000;
//    }
//    println(z)
    
//    val x = Sale.listSalesFilter()
//    println(x)
    Ok(html.index(Filter.filterForm, Sale.listSales))
  }

}