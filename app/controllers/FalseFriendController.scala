package controllers

import play.api.mvc.Controller
import play.api.mvc.Action
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.data.format.Formats._
import squeryl.FalseFriend
import service.FriendsServiceComponent
import service.FriendWordServiceComponent
import squeryl.FalseFriendId
import squeryl.FriendWord

object FalseFriendController
  extends Controller
  with FriendsServiceComponent
  with FriendWordServiceComponent {

  val falseFriendForm = Form(
    mapping("text" -> text)(t => FalseFriend(t))(f => Option(f.text))
    )

  val friendWordForm = Form(
    mapping("friendId" -> of[Long],
      "languageId" -> of[Long],
      "word" -> text)(FriendWord.apply)(FriendWord.unapply))

  def search(text: String = null) = Action {
    Ok(views.html.falsefriends(falseFriendService.searchBy(text)))
  }

  def card(friendId: Long) = Action {
    val typedId = FalseFriendId(friendId)
    Ok(views.html.falsefriendcard(falseFriendService.lookup(typedId).get, friendWordService.selectBy(typedId)))
  }

  def addFalseFriend = Action {
    implicit request =>
      falseFriendForm.bindFromRequest.value map {
        f =>
          val id = falseFriendService.create(f)
          Redirect(routes.FalseFriendController.card(id.id))
      } getOrElse BadRequest
  }

  def addFriendWord = Action {
    implicit request =>
      friendWordForm.bindFromRequest.value map {
        w =>
          val id = friendWordService.create(w)
          Redirect(routes.FalseFriendController.card(id.id))
      } getOrElse BadRequest
  }
}