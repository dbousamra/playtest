package controllers

import play.api._
import play.api.mvc._
import controllers.Authentication._
import views._
import models.User

object Management { 

  def WithUser(f: AuthenticatedRequest => Result): Action[AnyContent] = {
    Authenticated { request =>
      val user = request.user
      user match {
        case Some(x) => f(request)
        case None => Ok("no user")
      }
    }
  }

  def dashboard() = WithUser { implicit request =>
  	Ok(html.user.dashboard(request.user.get))
  }
  
  def profile() = WithUser { implicit request =>
    Ok(html.user.profile(request.user.get))
  }

}