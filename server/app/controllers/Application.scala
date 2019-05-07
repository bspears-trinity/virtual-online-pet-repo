package controllers

import javax.inject._
import models._
import play.api.data._
import play.api.data.Forms._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def map = Action {
    Ok(views.html.map())
  }
  
  def events = Action {
    Ok(views.html.events())
  }
  
  def pet = Action {
    Ok(views.html.pet())
  }

  def settings = Action {
    Ok(views.html.settings())
  }
  
  def logout = Action { implicit request =>
    Redirect(routes.Application.login).withNewSession
  }
  
  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }
  
  def login = Action {
    Ok(views.html.login())
  }

  def accountCreation = Action {
    Ok(views.html.accountCreation())
  }

  def changePW = Action {
    Ok(views.html.changePW())
  }
  
  def chooseYourPet = Action {
    Ok(views.html.chooseYourPet("Username"))
  }
}
