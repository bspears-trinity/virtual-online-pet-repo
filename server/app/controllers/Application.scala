package controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

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
    Redirect(routes.Application.map).withNewSession
  }
}
