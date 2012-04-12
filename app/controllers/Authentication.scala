package controllers
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import controllers._
import models._

case class AuthenticatedRequest(user: Option[User], request: Request[AnyContent]) extends WrappedRequest(request)

object Authentication extends Controller {

  def Authenticated(f: AuthenticatedRequest => Result) = {
    Action { request =>
      request.session.get("email").flatMap(u => Users.findByEmail(u)).map { user =>
        f(AuthenticatedRequest(Some(user), request))
      }.getOrElse {
        Redirect(routes.Authentication.login()).withNewSession
      }
    }
  }

  def UnAuthenticated(f: AuthenticatedRequest => Result) = {
    Action { request =>
      request.session.get("email").flatMap(u => Users.findByEmail(u)).map { user =>
        f(AuthenticatedRequest(Some(user), request))
      }.getOrElse {
        f(AuthenticatedRequest(None, request))
      }
    }
  }

  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text) 
      verifying ("Invalid email or password", result => result match {
        case (email, password) => Users.authenticate(email, password).isDefined
      }))

  /**
   * Login page.
   */
  def login = Action { implicit request =>
    implicit val authRequest = new AuthenticatedRequest(None, request)
    Ok(html.login(loginForm))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    implicit val authRequest = new AuthenticatedRequest(None, request)
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.Authentication.login).flashing("error" -> "Your email or password was incorrect.")    
      },
      user => Redirect(routes.Management.dashboard).withSession("email" -> user._1))
  }

  def logout = Authenticated { implicit request =>
    Redirect(routes.Cars.listModels()).withNewSession
  }
}
