package controllers

import play.api._
import play.api.mvc._
import controllers.Authentication._
import views._
import models.User
import play.api.data.Forms._
import play.api.data._
import anorm._


object Management { 

  val managementHome = Redirect(routes.Management.dashboard)
  
  
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
    val user = request.user.get
    val form = SignUp.signupForm.fill(user)
    Ok(html.user.profile(user, form))
  }
  
  def update() = WithUser { implicit request =>
    val id = request.user.get.id.get
    editForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.user.profile(request.user.get, formWithErrors)),
      user => { 
        User.update(id, user) 
        managementHome.flashing("success" -> "Model %s has been updated")
        }
      )
  }
  
   val editForm: Form[User] = Form(
     mapping(
      "id" -> ignored(NotAssigned: Pk[Long]),
      "name" -> text(minLength = 4),
      "email" -> email,
      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
          "Passwords don't match", passwords => passwords._1 == passwords._2))
      
    
    {
      (id, name, email, passwords) => User(id, email,name, passwords._1) 
    }
    {
      user => Some(user.id, user.name, user.email, (user.password, ""))
    }
//    }.verifying(
//      // Add an additional constraint: The email must not be taken (you could do an SQL request here)
//      "An account with this email already exists.",
//      user => User.findByEmail(user.email).isDefined
//    )
  )

}