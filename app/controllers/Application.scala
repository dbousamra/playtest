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

  def index = Authenticated { implicit request =>
    Ok(html.index("Front page"))
  }

}