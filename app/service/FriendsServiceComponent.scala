package service
import squeryl.CRUDService
import squeryl.FalseFriend
import squeryl.FalseFriendId
import org.squeryl.PrimitiveTypeMode._
import squeryl.DatabaseSchema

trait GenericFriendsServiceComponent {
  def falseFriendService: FalseFriendService

  trait FalseFriendService extends CRUDService[FalseFriend, FalseFriendId] {
    def searchBy(searchText: String): Seq[FalseFriend]
  }
}

trait FriendsServiceComponent extends GenericFriendsServiceComponent {
  val falseFriendService = new FalseFriendServiceImpl

  class FalseFriendServiceImpl extends FalseFriendService with DatabaseSchema {
    def create(entity: FalseFriend) = inTransaction {
      falseFriends.insert(entity).typedId
    }

    def update(entity: FalseFriend) = inTransaction {
      falseFriends.update(entity)
    }

    def lookup(entityId: FalseFriendId) = inTransaction {
      falseFriends.lookup(entityId.id)
    }

    def delete(entityId: FalseFriendId) = inTransaction {
      falseFriends.delete(entityId.id)
    }

    def searchBy(searchText: String) = inTransaction {
      from(falseFriends, friendWords)((friend, word) =>
        where(word.word like searchText + "%")
          select (friend)).distinct.toSeq
    }
  }
}
