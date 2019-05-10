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
        val user = request.session.get("username").getOrElse("MissingNo")
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
        found.flatMap(b => if(b) {
    	    Future.successful(Ok(views.html.accountCreation(loginForm)))
    	  } else {
    	    models.PetDBModel.addUser(credentials.username, credentials.password, db)
    	    Future.successful(Ok(views.html.login(loginForm)))
    	  })
      }
    )
  }
  
  def changePassword = Action.async { implicit request => 
    //TODO: Verify old password and update database with new password, then return settings view
    changePasswordForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.changePW(formWithErrors))),
      passes => {
        val user = request.session.get("username").getOrElse("MissingNo")
        val changePass = models.PetDBModel.changePass(user, passes.oldPassword, passes.newPassword, db)
        changePass.flatMap(c => {
          Future.successful(Ok(views.html.map()))
        })
      }
    )
    
    //Ok(views.html.index("Password changed: redirect to settings view"))
  }
  
  def newPet(petImg: Int) = Action.async { implicit request => 
    val postBody = request.body.asFormUrlEncoded
    postBody.map { args =>
      try {
        val user = request.session.get("username").getOrElse("MissingNo")
        val pet = args("petName").head.toString
        val addpet = models.PetDBModel.addPet(user, pet, petImg, db)
        addpet.flatMap { s =>
          models.PetDBModel.newStats(user, db).flatMap { g =>
            Future.successful(Ok(views.html.map()))
          }
        }
      } catch {
        case ex: NumberFormatException => Future.successful(Redirect("login", 200))
      }
    }.getOrElse(Future.successful(Redirect("login", 200)))
  }
  
  def abandonPet = Action.async { implicit request => 
    //TODO: Clear pet info from database.
    val user = request.session.get("username").getOrElse("MissingNo")
    val gonepet = models.PetDBModel.removePet(user, db)
    gonepet.flatMap { g =>
      Future.successful(Ok(views.html.chooseYourPet()))
    }
  }
  
  def walkPet = Action.async { implicit request =>
    //TODO: Check if walk is possible with params. If so, apply walk event to model, then update database. 
    //TODO: Call this route from main js file and display appropriate notification/graphic with JQuery.
    val user = request.session.get("username").getOrElse("MissingNo")
    val walk = models.PetDBModel.addEvent(user, 10, db)
    val Event = models.PetDBModel.getEvent(10, db)
    Event.map { e =>
      models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
      models.PetDBModel.updateMoney(user, e.moneyinc, db)
      Ok("Pet walked")
    }
  }
  
  def showPet = Action.async { implicit request =>
    //TODO: Check if show is possible with params. If so, apply show event to model, then update database. 
    //TODO: Call this route from main js file and display appropriate notification/graphic with JQuery.
    val user = request.session.get("username").getOrElse("MissingNo")
    val walk = models.PetDBModel.addEvent(user, 11, db)
    val Event = models.PetDBModel.getEvent(11, db)
    Event.map { e =>
      models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
      models.PetDBModel.updateMoney(user, e.moneyinc, db)
      Ok("Pet shown")
    }
  }
  
  def groomPet = Action.async { implicit request =>
    //TODO: Check if groom is possible with params. If so, apply groom event to model, then update database. 
    //TODO: Call this route from main js file and display appropriate notification/graphic with JQuery.
    val user = request.session.get("username").getOrElse("MissingNo")
    val walk = models.PetDBModel.addEvent(user, 9, db)
    val Event = models.PetDBModel.getEvent(9, db)
    Event.map { e =>
      models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
      models.PetDBModel.updateMoney(user, e.moneyinc, db)
      Ok("Pet groomed")
    }
  }
  
  def buyItem(itemID: Int) = Action.async { implicit request =>
    //Check if buy is possible with current money. If so, update parameters, then update database.
    //TODO: Use JQuery to update money label
    val user = request.session.get("username").getOrElse("MissingNo")
    val walk = models.PetDBModel.addEvent(user, itemID+6, db)
    val Event = models.PetDBModel.getEvent(itemID+6, db)
    Event.flatMap { e =>
      val mon = models.PetDBModel.getMoney(user, db)
      mon.flatMap(m => {
        if(m.money + e.moneyinc > 0) {
          models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
          models.PetDBModel.updateMoney(user, e.moneyinc, db)
          Future.successful(Ok("Item bought"))
        } else {
          Future.successful(Ok("Not enough money"))
        }
      })
    }
  }
  
  def updateEvent = Action.async { implicit request => 
    //Check current time. Get last update time from model. Generate and apply events based on time passed. Update last update time. Update database.
    val user = request.session.get("username").getOrElse("MissingNo")
    val lastvisit = models.PetDBModel.getLastVisit(user, db)
    lastvisit.flatMap { l =>
      val date = new java.util.Date()
      val sqlDate = new java.sql.Date(date.getTime())
      if(l.updatelast.getOrElse(sqlDate).getMinutes < sqlDate.getMinutes - 5) {
        val r = math.random()
        if(r < 0.2) {
          val walk = models.PetDBModel.addEvent(user, 1, db)
          val Event = models.PetDBModel.getEvent(1, db)
          Event.map { e =>
            models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
            models.PetDBModel.updateMoney(user, e.moneyinc, db)
          }
        } else if (r < 0.3) {
          val walk = models.PetDBModel.addEvent(user, 2, db)
          val Event = models.PetDBModel.getEvent(2, db)
          Event.map { e =>
            models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
            models.PetDBModel.updateMoney(user, e.moneyinc, db)
          }
        } else if (r < 0.5) {
          val walk = models.PetDBModel.addEvent(user, 3, db)
          val Event = models.PetDBModel.getEvent(3, db)
          Event.map { e =>
            models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
            models.PetDBModel.updateMoney(user, e.moneyinc, db)
          }
        } else if (r < 0.6) {
          val walk = models.PetDBModel.addEvent(user, 4, db)
          val Event = models.PetDBModel.getEvent(4, db)
          Event.map { e =>
            models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
            models.PetDBModel.updateMoney(user, e.moneyinc, db)
          }
        } else {
          val walk = models.PetDBModel.addEvent(user, 5, db)
          val Event = models.PetDBModel.getEvent(5, db)
          Event.map { e =>
            models.PetDBModel.updateStats(user, e.affectioninc, e.hungerinc, e.exhaustioninc, db)
            models.PetDBModel.updateMoney(user, e.moneyinc, db)
          }
        }
        models.PetDBModel.addVisit(user, db)
        Future.successful(Ok("Added event"))
      } else {
        Future.successful(Ok("No new event"))
      }
    }
  }

  def newNotifications = Action.async { implicit request =>
    //TODO: Check user's notifications for any that need to be displayed. Return new notifications and update database to show that they have been displayed.
    val user = request.session.get("username").getOrElse("MissingNo")
    val newnotes = models.PetDBModel.getNotif(user, db)
    Future.successful(Ok("New Notifications returned"))
  }
}

