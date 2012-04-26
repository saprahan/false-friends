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

  trait FriendWordService extends CRUDService[FriendWord, FriendWordId] {
    def selectBy(falseFriendId: FalseFriendId): Seq[FriendWord]
  }
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
      friendWords.delete(entityId.id)
    }

    def selectBy(falseFriendId: FalseFriendId) = inTransaction {
      from(friendWords)(fW =>
        where(fW.friendId === falseFriendId.id)
          select (fW)
          orderBy (fW.languageId)).toSeq
    }
  }
}
