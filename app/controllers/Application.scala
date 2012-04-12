package controllers

import play.api._
import controllers._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import controllers.Authentication._
import controllers._
import views._
import models._
import java.text.NumberFormat
import java.util.Locale

object Application extends Controller {

  def index = Authenticated { implicit request =>
    Ok("INDEX")
  }

}