package edu.trinity.webapps.controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class VOPController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  
  //TODO: Change view names to actual names used.
  
  //Simple view actions that return a view without additional processing.
  //TODO: Check to ensure that user is logged in for all Get routes, and load error screen if not.
  
  def loginView = Action {
    Ok(views.html.index("Login view"))
  }
  
  def registerView = Action {
    Ok(views.html.index("Register view"))  
  }
  
  def changePasswordView = Action {
    Ok(views.html.index("Change password view"))
  }
  
  def chooseNewPetView = Action {
    Ok(views.html.index("Choose new pet view"))
  }
  
  def mainView = Action {
    Ok(views.html.index("Main view"))
  }
  
  def shopView = Action {
    Ok(views.html.index("Shop view"))
  }
  
  def settingsView = Action {
    Ok(views.html.index("Settings view"))
  }
  
  def eventsView = Action {
    Ok(views.html.index("Events view"))
  }
  
  def petView = Action {
    Ok(views.html.index("Pet view"))
  }
  
  //More involved actions that get form data and manipulate model before redirecting.
  
  def login = Action { implicit request =>
    //TODO: Verify user credentials through database, load user info into model, start session and return main page
    Ok(views.html.index("Logged in: redirect to main view"))
  }
  
  def logout = Action { implicit request => 
    //TODO: Clear user info and end session, then return login view.
    Ok(views.html.index("Logged out: redirect to login view"))
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
    Ok(views.html.index("User deleted: redirect to login view"))
  }
  
  def newPet = Action { implicit request => 
    //TODO: Get form data on new pet and add to database through model.
    Ok(views.html.index("New pet chosen: redirect to Main view"))
  }
  
  def abandonPet = Action { implicit request => 
    //TODO: Clear pet info from database.
    Ok(views.html.index("Pet abandoned: redirect to Choosing view"))
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
}

