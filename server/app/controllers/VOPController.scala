package controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

case class LoginData(username: String, password: String)
case class PasswordData(oldPassword: String, newPassword:String)

@Singleton
class VOPController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginData.apply)(LoginData.unapply))

  val changePasswordForm = Form(mapping( 
    "oldPassword" -> nonEmptyText,
    "newPassword" -> nonEmptyText
  )(PasswordData.apply)(PasswordData.unapply))

  //TODO: Change view names to actual names used.
  
  //Simple view actions that return a view without additional processing.
  //TODO: Check to ensure that user is logged in for all Get routes, and load error screen if not.
  
  def loginView = Action {
    Ok(views.html.login())
  }
  
  def registerView = Action {
    Ok(views.html.accountCreation())  
  }
  
  def changePasswordView = Action {
    Ok(views.html.index("Change password view"))
  }
  
  def chooseNewPetView = Action {
    Ok(views.html.chooseYourPet())
  }
  
  def mainView = Action {
    Ok(views.html.map())
  }
  
  def shopView = Action {
    //get money from DB
    Ok(views.html.shop(10))
  }
  
  def settingsView = Action {
    Ok(views.html.settings())
  }
  
  def eventsView = Action {
    Ok(views.html.events())
  }
  
  def petView = Action {
    //get hunger/affection/exhaustion from DB
    Ok(views.html.pet(10,10,10))
  }
  
  //More involved actions that get form data and manipulate model before redirecting.
  
  def login = Action { implicit request =>
    //TODO: Verify user credentials through database, load user info into model, start session and return main page
    Ok(views.html.index("Logged in: redirect to main view"))
  }
  
  def logout = Action { implicit request => 
    //TODO: Clear user info and end session, then return login view.
    Ok(views.html.login()).withNewSession
  }
  
  def register = Action { implicit request =>
    //TODO: Update database with new user info through model, start session, and return choosing view.
    Ok(views.html.index("Registered new user: redirect to Choosing view"))  
  }
  
  def changePassword = Action { implicit request => 
    //TODO: Verify old password and update database with new password, then return settings view
    Ok(views.html.index("Password changed: redirect to settings view"))
  }
  
  def deleteUser = Action { implicit request =>
    //TODO: Clear user info from database, end session, and return login view
    Ok(views.html.login()).withNewSession
  }
  
  def newPet = Action { implicit request => 
    //TODO: Get form data on new pet and add to database through model.
    Ok(views.html.map())
  }
  
  def abandonPet = Action { implicit request => 
    //TODO: Clear pet info from database.
    Ok(views.html.chooseYourPet())
  }
  
  def walkPet = Action { implicit request =>
    //TODO: Check if walk is possible with params. If so, apply walk event to model, then update database. 
    //TODO: Call this route from main js file and display appropriate notification/graphic with JQuery.
    Ok("Pet walked")
  }
  
  def showPet = Action { implicit request =>
    //TODO: Check if show is possible with params. If so, apply show event to model, then update database. 
    //TODO: Call this route from main js file and display appropriate notification/graphic with JQuery.
    Ok("Pet shown")
  }
  
  def groomPet = Action { implicit request =>
    //TODO: Check if groom is possible with params. If so, apply groom event to model, then update database. 
    //TODO: Call this route from main js file and display appropriate notification/graphic with JQuery.
    Ok("Pet groomed")
  }
  
  def buyItem = Action { implicit request =>
    //TODO: Check if buy is possible with current money. If so, update parameters, then update database.
    //TODO: Use JQuery to update money label
    Ok("Item bought")
  }
  
  def updateEvent = Action { implicit request => 
    //TODO: Check current time. Get last update time from model. Generate and apply events based on time passed. Update last update time. Update database.
    Ok("Event updated")
  }

  def newNotifications = Action { implicit request =>
    //TODO: Check user's notifications for any that need to be displayed. Return new notifications and update database to show that they have been displayed.
    Ok("New Notifications returned")
  }
}

