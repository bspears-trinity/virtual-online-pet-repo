package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import scala.concurrent.ExecutionContext
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class LoginData(username: String, password: String)
case class PasswordData(oldPassword: String, newPassword:String)
case class PetData(name: String, pic: Int)

@Singleton
class VOPController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents) 
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginData.apply)(LoginData.unapply))

  val changePasswordForm = Form(mapping( 
    "oldPassword" -> nonEmptyText,
    "newPassword" -> nonEmptyText
  )(PasswordData.apply)(PasswordData.unapply))
  
  val newPetForm = Form(mapping(
      "name" -> nonEmptyText,
      "pic" -> number(0, 5)
  )(PetData.apply)(PetData.unapply))
  
  //Simple view actions that return a view without additional processing.
  //TODO: Check to ensure that user is logged in for all Get routes, and load login screen if not.
  
  def loginView = Action {
    Ok(views.html.login(loginForm))
  }
  
  def registerView = Action {
    Ok(views.html.accountCreation(loginForm))  
  }
  
  def changePasswordView = Action {
    Ok(views.html.changePW(changePasswordForm))
  }
  
  def chooseNewPetView = Action {
    Ok(views.html.chooseYourPet())
  }
  
  def mainView = Action {
    Ok(views.html.map())
  }
  
  def shopView = Action.async { implicit request => {
    Future.successful(Ok(views.html.shop(1)))
  }}
  
  def settingsView = Action {
    Ok(views.html.settings())
  }
  
  def eventsView = Action {
    Ok(views.html.events())
  }
  
  def petView = Action.async { implicit request =>
    val postBody = request.body.asFormUrlEncoded
    postBody.map { args =>
      try {
        val user = args("username").head.toString
        val stats = models.PetDBModel.getStats(user, db)
        stats.map { s =>
          Ok(views.html.pet(s.hunger,s.affection,s.exhaustion))
        }
      } catch {
        case ex: NumberFormatException => Future.successful(Redirect("login", 200))
      }
    }.getOrElse(Future.successful(Redirect("login", 200)))
  }
  
  //More involved actions that get form data and manipulate model before redirecting.
  
  def login = Action.async { implicit request =>
    //Gets user credentials from form, then verifies them through the database. If valid, starts a user session and directs to main page. Else, return to login page.
    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.login(formWithErrors))),
      credentials => {
        val isValid = models.PetDBModel.getLogin(credentials.username, credentials.password, db)
        isValid.map(valid => {
          if (valid) {
            Redirect(routes.VOPController.mainView).withSession("username" -> credentials.username)
          } else {
            BadRequest(views.html.login(loginForm))
          }
        })    
      }
    )
  }
  
  def logout = Action { implicit request => 
    //Clear user info and end session, then return login view.
    Ok(views.html.login()).withNewSession
  }
  
  def register = Action.async { implicit request =>
    //Update database with new user info through model, start session, and return choosing view.
    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.accountCreation(formWithErrors))),
      credentials => {
        val found = models.PetDBModel.findUser(credentials.username, db)
        found.flatMap(b => if(!b) {
          val addUser = models.PetDBModel.addUser(credentials.username, credentials.password, db)
          Future.successful(Ok(views.html.login(loginForm)))
          /*addUser.map(result => {
            Ok(views.html.chooseYourPet())
            //TODO: Use result to determine success. On failure, return to accountCreation view.
            //Redirect(routes.VOPController.chooseNewPetView).withSession("username" -> credentials.username)
          })*/
        })
      }
    )
  }
  
  def changePassword = Action { implicit request => 
    //TODO: Verify old password and update database with new password, then return settings view
    Ok(views.html.index("Password changed: redirect to settings view"))
  }
  
  def deleteUser = Action { implicit request =>
    //TODO: Clear user info from database, end session, and return login view
    Ok(views.html.login()).withNewSession
  }
  
  def newPet(petImg: Int) = Action { implicit request => 
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
  
  def buyItem(itemID: Int) = Action { implicit request =>
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

