package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

@Singleton
class MessagesController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  case class UserData(name: String, age: Int)

  def getMessage() = Action{ implicit request: Request[AnyContent] =>
    Ok(views.html.message(""))
  }

  def getMessagePathParam(message: String) = Action{ implicit request: Request[AnyContent] =>
    Ok(views.html.message(message))
  }

  def sendUser(username: String, userage: Int) = Action {implicit request: Request[AnyContent] =>
    Ok(views.html.user(username, userage))
  }
}
