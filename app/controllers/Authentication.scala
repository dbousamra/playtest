package controllers
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import models._
import models.cars._

case class AuthenticatedRequest(user: Option[User], request: Request[AnyContent]) extends WrappedRequest(request) 

object Authentication extends Controller {
  
  def Authenticated(f: AuthenticatedRequest => Result) = {
    Action { request =>
      request.session.get("email").flatMap(u => User.findByEmail(u)).map { user =>
        f(new AuthenticatedRequest(Some(user), request))
      }.getOrElse{
        println("ELSE")
        f(new AuthenticatedRequest(None, request))
        }
    }
  }

//  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.hom)
    private def onUnauthorized(request: RequestHeader) = {
       println("sdfsf")
       Results.Redirect(routes.Authentication.login).withNewSession
//       Results.Redirect(routes.Application.hom).withNewSession

    }


  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text) verifying ("Invalid email or password", result => result match {
        case (email, password) => User.authenticate(email, password).isDefined
      }))

  /**
   * Login page.
   */
  def login = Action { implicit request =>
    Ok(html.login(loginForm))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.Application.index).withSession("email" -> user._1))
  }

  def logout = Action {
    Redirect(routes.Cars.listModels()).withNewSession.flashing(
      "success" -> "You've been logged out")
  }

}
