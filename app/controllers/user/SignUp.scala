package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import controllers.Authentication._
import views._
import models._
import anorm._

object SignUp extends Controller {
  
  /**
   * Sign Up Form definition.
   *
   * Once defined it handle automatically, ,
   * validation, submission, errors, redisplaying, ...
   */
  val signupForm: Form[User] = Form(
    
    // Define a mapping that will handle User values
    mapping(
      "id" -> ignored(NotAssigned: Pk[Long]),
      "name" -> text(minLength = 4),
      "email" -> email,
      
      // Create a tuple mapping for the password/confirm
      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
        // Add an additional constraint: both passwords must match
        "Passwords don't match", passwords => passwords._1 == passwords._2
      ),
      
      "accept" -> checked("You must accept the conditions")
      
    )
    // The mapping signature doesn't match the User case class signature,
    // so we have to define custom binding/unbinding functions
    {
      // Binding: Create a User from the mapping result (ignore the second password and the accept field)
      (id, name, email, passwords, _) => new User(0, email,name, passwords._1) 
    }
    {
      // Unbinding: Create the mapping values from an existing User value
      user => Some(Id(user.id), user.name, user.email, (user.password, ""), false)
    }.verifying(
      // Add an additional constraint: The email must not be taken (you could do an SQL request here)
      "An account with this email already exists.",
      user => !Users.findByEmail(user.email).isDefined
    )
  )
  
  /**
   * Display an empty form.
   */
  def form = UnAuthenticated { implicit request =>
    Ok(html.user.signUpForm(signupForm));
  }
  
  
  /**	
   * Handle form submission.
   */
  def submit = UnAuthenticated { implicit request =>
    signupForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => {
        println("Bad request")
        BadRequest(html.user.signUpForm(errors))
      }
      ,
      user => {
        Users.create(user)
        Ok(html.user.signUpSummary(user))
      }
    )
  }
  
}