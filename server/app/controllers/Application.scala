package controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def mainPage = Action {
    Ok(views.html.mainPage())
  }
  
  def events = Action {
    Ok(views.html.events())
  }
  
  def petPage = Action {
    Ok(views.html.petPage())
  }

  def settings = Action {
    Ok(views.html.settings())
  }
  
  def logout = Action { implicit request =>
    Redirect(routes.Application.mainPage).withNewSession
  }
}
