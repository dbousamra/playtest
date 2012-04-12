package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import controllers._
import controllers.Authentication._
import models._
import models.Models._
import java.text.NumberFormat
import java.util.Locale
import java.sql.Blob
import play.api.libs.iteratee.Enumerator
import java.sql.Date

object Cars extends Controller {

  val modelsHome = Redirect(routes.Cars.listModels(0, 2, ""))

  def listModels(page: Int, orderBy: Int, filter: String) = UnAuthenticated { implicit request =>
    Ok {
      html.cars.listModels(
        Models.list(page = page,
          orderBy = orderBy,
          filter = ("%" + filter + "%")),
        orderBy,
        filter)
    }
  }

  def edit(id: Long) = UnAuthenticated { implicit request =>
    Models.findById(id).map { model =>
      Ok(html.cars.editForm(id, modelForm.fill(model)))
    }.getOrElse(Unauthorized)
  }

  def update(id: Long) = UnAuthenticated { implicit request =>
    modelForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.cars.editForm(id, formWithErrors)),
      model => {
        Models.update(id, model)
        Redirect(routes.Cars.edit(id)).flashing("success" -> "Model %s has been updated".format(model.name))
      })
  }

  def create = UnAuthenticated { implicit request =>
    Ok(html.cars.createForm(modelForm))
  }

  def save = Authenticated { implicit request =>
    modelForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.cars.createForm(formWithErrors)).flashing("error" ->
        "Model %s has not been correctly updated. Check form and try again"),
      model => {
        Models.insert(model)
        modelsHome.flashing("success" -> "Model %s has been created".format(model.name))
      })
  }
  def delete(id: Long) = Action {
    Models.delete(id)
    modelsHome.flashing("success" -> "Model has been deleted")
  }

  val modelForm = Form(
    mapping(
      "id" -> ignored(longNumber),
      "make" -> longNumber,
      "modelDetails" -> longNumber,
      "name" -> nonEmptyText,
      "year" -> date("yyyy-MM-dd"),
      "trim" -> optional(nonEmptyText),
      "seats" -> optional(number),
      "doors" -> optional(number),
      "body" -> optional(nonEmptyText)) 
      {
        (id, make, modelDetails, name, year, trim, seats, doors, body) =>
          (new Model(
            0, make, modelDetails, 
            name, Some(new Date(year.getTime())), trim, seats, doors, body))
      } {
        model =>
          Some(
            ignored(model.id), model.makeId, model.modelDetailId, model.name, new java.util.Date(), model.trim, model.seats, model.doors, model.body)
      })
}