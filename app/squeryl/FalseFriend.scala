package squeryl
import java.sql.Timestamp
import org.squeryl.KeyedEntity
import org.squeryl.{Schema}
import org.squeryl.PrimitiveTypeMode._


case class FalseFriendId(id: Long) extends LongTypedId

case class FalseFriend(
                   text: String,
                   createdate: Timestamp
                     ) extends KeyedEntity[Long] {
  val id:Long = 0

  def typedId = FalseFriendId(id)
}

object FalseFriend extends DatabaseSchema with CRUDService[FalseFriend, FalseFriendId] {
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

	def selectAll = inTransaction {
		from(falseFriends)(f => 
		select(f) 
		orderBy(f.text)
				).toSeq
	}
}