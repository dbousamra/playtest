package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import views._
import Application._
import Authentication._
import models._
import models.cars._
import models.market._
import java.text.NumberFormat
import java.util.Locale
import java.sql.Blob
import play.api.libs.iteratee.Enumerator

object Cars extends Controller {

  val modelsHome = Redirect(routes.Cars.listModels(0, 2, ""))
  
  /**
   * Display the paginated list of models.
   *
   * @param page Current page number (starts from 0)
   * @param orderBy Column to be sorted
   * @param filter Filter applied on model names
   */
  def listModels(page: Int, orderBy: Int, filter: String) = Authenticated { implicit request =>
    Ok {
      html.cars.listModels(
        Model.list(page = page, orderBy = orderBy, filter = ("%" + filter + "%")), orderBy, filter)
    }
  }

  /**
   * Display the 'edit form' of a existing Model.
   *
   * @param id Id of the model to edit
   */
  def edit(id: Long) = Authenticated { implicit request =>
    Model.findById(id).map { model =>
      Ok(html.cars.editForm(id, modelForm.fill(model)))
    }.getOrElse(Unauthorized)
  }

  /**
   * Handle the 'edit form' submission
   *
   * @param id Id of the model to edit
   */
  def update(id: Long) = Authenticated { implicit request =>
    modelForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.cars.editForm(id, formWithErrors)),
      model => {
        Model.update(id, model)
        modelsHome.flashing("success" -> "Model %s has been updated".format(model.name))
      })
  }

  /**
   * Display the 'new model form'.
   */
  def create = Authenticated { implicit request =>
    Ok(html.cars.createForm(modelForm))
  }

  /**
   * Handle the 'new model form' submission.
   */
  def save = Authenticated { implicit request =>
    modelForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.cars.createForm(formWithErrors)),
      model => {
        Model.insert(model)
        modelsHome.flashing("success" -> "Model %s has been created".format(model.name))
      })
  }

  /**
   * Handle model deletion.
   */
  def delete(id: Long) = Action {
    Model.delete(id)
    modelsHome.flashing("success" -> "Model has been deleted")
  }

  /**
   * Describe the model form (used in both edit and create screens).
   */
  val modelForm = Form(
    mapping(
      "id" -> ignored(NotAssigned: Pk[Long]),
      "name" -> nonEmptyText,
      "introduced" -> optional(date("yyyy-MM-dd")),
      "discontinued" -> optional(date("yyyy-MM-dd")),
      "aspiration" -> optional(longNumber),
      "make" -> optional(longNumber))(Model.apply)(Model.unapply))

  // -- Actions
}