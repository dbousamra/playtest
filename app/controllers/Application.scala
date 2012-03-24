package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
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
  val Home = Redirect(routes.Application.list(0, 2, ""))
  
  /**
   * Describe the model form (used in both edit and create screens).
   */ 
  val modelForm = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "introduced" -> optional(date("yyyy-MM-dd")),
      "discontinued" -> optional(date("yyyy-MM-dd")),
      "aspiration" -> optional(longNumber),
      "make" -> optional(longNumber)
    )(Model.apply)(Model.unapply)
  )
  
  // -- Actions

  /**
   * Handle default path requests, redirect to models list
   */  
  def index = Action { Home }
  
  /**
   * Display the paginated list of models.
   *
   * @param page Current page number (starts from 0)
   * @param orderBy Column to be sorted
   * @param filter Filter applied on model names
   */
  def list(page: Int, orderBy: Int, filter: String) = Action { implicit request =>
    Ok(html.list(
      Model.list(page = page, orderBy = orderBy, filter = ("%"+filter+"%")),
      orderBy, filter
    ))
  }
  
  /**
   * Display the 'edit form' of a existing Model.
   *
   * @param id Id of the model to edit
   */
  def edit(id: Long) = Action {
    Model.findById(id).map { model =>
      Ok(html.editForm(id, modelForm.fill(model)))
    }.getOrElse(NotFound)
  }
  
  /**
   * Handle the 'edit form' submission 
   *
   * @param id Id of the model to edit
   */
  def update(id: Long) = Action { implicit request =>
    modelForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.editForm(id, formWithErrors)),
      model => {
        Model.update(id, model)
        Home.flashing("success" -> "Model %s has been updated".format(model.name))
      }
    )
    }
  
  /**
   * Display the 'new model form'.
   */
  def create = Action {
    Ok(html.createForm(modelForm))
  }
  
  /**
   * Handle the 'new model form' submission.
   */
  def save = Action { implicit request =>
    modelForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.createForm(formWithErrors)),
      model => {
        Model.insert(model)
        Home.flashing("success" -> "Model %s has been created".format(model.name))
      }
    )
  }
  
  /**
   * Handle model deletion.
   */
  def delete(id: Long) = Action {
    Model.delete(id)
    Home.flashing("success" -> "Model has been deleted")
  }

}