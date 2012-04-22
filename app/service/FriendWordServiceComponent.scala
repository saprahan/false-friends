package service
import squeryl.CRUDService
import squeryl.FalseFriend
import squeryl.FalseFriendId
import org.squeryl.PrimitiveTypeMode._
import squeryl.DatabaseSchema
import squeryl.FriendWord
import squeryl.FriendWordId

trait GenericFriendWordServiceComponent {
  def friendWordService: FriendWordService

  trait FriendWordService extends CRUDService[FriendWord, FriendWordId]
}

trait FriendWordServiceComponent extends GenericFriendWordServiceComponent {
  val friendWordService = new FriendWordServiceImpl

  class FriendWordServiceImpl extends FriendWordService with DatabaseSchema {
    def create(entity: FriendWord) = inTransaction {
      friendWords.insert(entity).typedId
    }

    def update(entity: FriendWord) = inTransaction {
      friendWords.update(entity)
    }

    def lookup(entityId: FriendWordId) = inTransaction {
      friendWords.lookup(entityId.id)
    }

    def delete(entityId: FriendWordId) = inTransaction {
      falseFriends.delete(entityId.id)
    }
  }
}
