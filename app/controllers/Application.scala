package controllers

import play.api.mvc._
import com.codahale.jerkson.Json
import play.api.data.Form
import play.api.data.Forms.{mapping, text, optional}
import org.squeryl.PrimitiveTypeMode._
import squeryl.{DatabaseSchema, Language}

object Application extends Controller {

  val languageForm = Form(
    mapping(
      "name" -> text,
      "nameEnglish" -> text,
      "code" -> text
    )(Language.apply)(Language.unapply)
  )

  def index = Action {
    Ok(views.html.index("False friends project"))
  }

  def listLanguages = Action {
    val json = inTransaction {
      val languages = Language.selectAll
      Json.generate(languages)
    }
    Ok(json).as(JSON)
  }

  def addLanguage = Action { implicit request =>
    languageForm.bindFromRequest.value map { l =>
      inTransaction(DatabaseSchema.languages insert l)
      Redirect(routes.Application.index())
    } getOrElse BadRequest
  }

}