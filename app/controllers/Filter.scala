package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import Application._

case class Filter(modelId: Long)

case class FilteredRequest(user: Option[User], request: Request[AnyContent]) extends WrappedRequest(request) 

object Filter {
  
  def Filtered(f: FilteredRequest => Result) = {
    Action { request =>
      request.session.get("email").flatMap(u => User.findByEmail(u)).map { user =>
        f(new AuthenticatedRequest(Some(user), request))
      }.getOrElse{
        f(new AuthenticatedRequest(None, request))
        }
    }
  }
  
  

  def save = Action { implicit request =>
    Ok("Test filter submit")

  }

  val filterForm = Form[Filter](
    mapping(
      "modelId" -> longNumber
      )(Filter.apply)(Filter.unapply))

}