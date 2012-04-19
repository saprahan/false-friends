package controllers

import play.api.mvc._
import com.codahale.jerkson.Json
import play.api.data.Form
import play.api.data.Forms.{mapping, text, optional}
import org.squeryl.PrimitiveTypeMode._
import squeryl.{DatabaseSchema, Language}
import service.LanguageService
import com.google.inject.Inject
import play.Logger
import com.google.inject.Injector
import com.google.inject.Guice
import service.LanguageServiceImpl
import module.Dependencies

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
    
     val inj:Injector = Guice.createInjector(new Dependencies)
    
    val languageService:LanguageService = inj.getInstance(classOf[LanguageService])   
    
    val json = inTransaction {
      val languages = languageService.selectAll
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