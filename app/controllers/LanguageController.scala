package controllers

import com.codahale.jerkson.Json
import play.api.data.Forms.mapping
import play.api.data.Forms.text
import play.api.data.Form
import play.api.mvc.Action
import play.api.mvc.Controller
import play.Logger
import service.LanguageServiceComponent
import squeryl.Language
import org.squeryl.SessionFactory
import javax.annotation.PostConstruct

object LanguageController
  extends Controller
  with LanguageServiceComponent {

  val languageForm = Form(
    mapping(
      "name" -> text,
      "nameEnglish" -> text,
      "code" -> text)(Language.apply)(Language.unapply))

  def index = Action {
    Ok(views.html.languages(allLanguages))
  }

  def listLanguages = Action {
    val json = Json.generate(allLanguages)
    Ok(json).as(JSON)
  }

  private def allLanguages = languageService.selectAll

  def addLanguage = Action {
    implicit request =>
      languageForm.bindFromRequest.value map {
        l =>
          languageService.create(l)
          Redirect(routes.LanguageController.index())
      } getOrElse BadRequest
  }
}