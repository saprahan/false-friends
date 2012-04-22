package controllers

import play.api.mvc.Controller
import service.LanguageServiceComponent
import play.api.mvc.Action

object Application extends Controller {
  def index = Action {
    Redirect("/languages/")
  }
}