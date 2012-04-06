package controllers

import play.api._
import play.api.mvc._
import controllers.Authentication._
import views._
import models._
import play.api.data.Forms._
import play.api.data._
import anorm._
import controllers._

object Management {

  val managementHome = Redirect(routes.Management.dashboard)

  def WithUser(f: AuthenticatedRequest => Result): Action[AnyContent] = {
    Authenticated { implicit request =>
      val user = request.user
      user match {
        case Some(x) => f(request)
        case None => Ok(html.login(loginForm))
      }
    }
  }

  def dashboard() = WithUser { implicit request =>
    val user = request.user.get
    val sales = Sale.listSalesByUserId(user.id.get)
    Ok(html.user.dashboard(request.user.get, sales))
  }

  def profile() = WithUser { implicit request =>
    val user = request.user.get
    val form = editProfileForm.fill(user.name, user.email)
    Ok(html.user.profile(user, form, changePasswordForm))
  }

  def update() = WithUser { implicit request =>
    val user = request.user.get
    println(user)
    editProfileForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.user.profile(request.user.get, formWithErrors, changePasswordForm)),
        data => {
        val newUser = User(user.id, data._2, data._1, user.password)
        User.update(user.id.get, newUser)
        Redirect(routes.Management.profile).withSession("email" -> newUser.email).flashing("success" -> "Your profile has been updated.")
      })
  }

  val changePasswordForm = Form(
    tuple(
      "main" -> text(minLength = 6),
      "confirm" -> text).verifying(
        "Passwords don't match", passwords => passwords._1 == passwords._2))

  val editProfileForm = Form(
    tuple(
      "name" -> text(minLength = 4),
      "email" -> email)
      )

}