package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

@Singleton
class MessagesController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def getMessage() = Action{ implicit request: Request[AnyContent] =>
    Ok(views.html.message())
  }
}
