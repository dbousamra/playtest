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

object Market extends Controller {
  
  val formatter = NumberFormat.getCurrencyInstance(Locale.US)
  formatter.setMaximumFractionDigits(0)
  
  def byId(id: Long) = Action { implicit request =>
    Ok(html.sales(
      Sale.byId(id), formatter
    ))
  }
  
  def list= Action { implicit request =>
    Ok(html.sales(
      Sale.list, formatter
    ))
  }

}