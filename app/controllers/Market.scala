package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import models._
import models.market._

object Market extends Controller {
  
  def list(id: Long) = Action { implicit request =>
    Ok(html.sales(
      Sale.list(id)
    ))
  }

}