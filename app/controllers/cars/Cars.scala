package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import controllers._
import controllers.Authentication._
import models._
import java.text.NumberFormat
import java.util.Locale
import java.sql.Blob
import play.api.libs.iteratee.Enumerator
import java.sql.Date
import models.Models._

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

  def update(id: Long) = UnAuthenticated { implicit request =>    modelForm.bindFromRequest.fold(
      formWithErrors => { 
        println("Error in update")
        BadRequest(html.cars.editForm(id, formWithErrors))
      },
      model => {
        println("INSAVE")
        Models.update(id, model)
        Redirect(routes.Cars.edit(id)).flashing("success" -> "Model %s has been updated".format(model.name))
      })
  }

  def create = UnAuthenticated { implicit request =>
    Ok(html.cars.createForm(modelForm))
  }

  def save = Authenticated { implicit request =>
    modelForm.bindFromRequest.fold(
      formWithErrors => {
        println("Error in save")
        BadRequest(html.cars.createForm(formWithErrors))
      },
      model => {
        Models.insert(model)
        modelsHome.flashing("success" -> "Model %s has been created".format(model.name))
      })
  }
  def delete(id: Long) = Authenticated { implicit request =>
    Models.delete(id)
    modelsHome.flashing("success" -> "Model has been deleted")
  }
  
  val modelForm = Form(
      mapping(
      "id" -> longNumber,
      "make" -> longNumber,
      "name" -> nonEmptyText,
      "year" -> date("yyyy-MM-dd"),
      "trim" -> optional(text),
      "seats" -> optional(number),
      "doors" -> optional(number),
      "body" -> optional(nonEmptyText),
      
      "modelDetails" -> mapping (
          "id" -> longNumber,
          "engine" -> mapping(
              "position" -> optional(text),
			  "cc" -> optional(number),
			  "cylinders" -> optional(number),
			  "engine_type" -> optional(text),
			  "valves" -> optional(number)
          )(Engine.apply)(Engine.unapply),
          "drivetrain" -> mapping(
              "drivetype" -> optional(text),
              "transmission" -> optional(text)
          )(Drivetrain.apply)(Drivetrain.unapply),
          "dimensions" -> mapping(
              "weight" -> optional(number),
			  "length" -> optional(number),
			  "width" -> optional(number),
			  "height" -> optional(number),
			  "wheelbase" -> optional(number)
          )(Dimensions.apply)(Dimensions.unapply),
          "economy" -> mapping(
              "highway" -> optional(number),
			  "mixed" -> optional(number),
			  "city" -> optional(number),
			  "tank_size" -> optional(number)
          )(Economy.apply)(Economy.unapply),
          "performance" -> mapping(
              "power" -> optional(number),
			  "power_rpm" -> optional(number),
			  "torque" -> optional(number),
			  "torque_rpm" -> optional(number)
          )(Performance.apply)(Performance.unapply)
      )(ModelDetailUnified.apply)(ModelDetailUnified.unapply)
    )
    (
        (id, makeId, name, year, trim, seats, doors, body, modelDetails) => 
          new ModelUnified(0, name, Some(new Date(year.getTime())), trim, seats, doors, body, Makes.findById(makeId), modelDetails)
    )
    (
        (model: ModelUnified) => Some(model.id, model.make.id, model.name, model.year.get, model.trim, model.seats, model.doors, model.body, model.details)
    )     
  )
}