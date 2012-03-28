package controllers

import play.api._
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
  
  /**
   * This result directly redirect to the application home.
   */
  val Home = Redirect(routes.Cars.listModels(0, 2, ""))
  
  /**
   * Handle default path requests, redirect to models list
   */  
  def index = Action { Home }
  
  

}