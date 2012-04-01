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
        f(new AuthenticatedRequest(None, request))
        }
    }
  }

    private def onUnauthorized(request: RequestHeader) = {
       Results.Redirect(routes.Authentication.login).withNewSession
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
  def login = Authenticated { implicit request =>
    Ok(html.login(loginForm))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Authenticated { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.Management.dashboard).withSession("email" -> user._1))
  }

  def logout = Authenticated { implicit request =>
    Redirect(routes.Cars.listModels()).withNewSession
  }
}
