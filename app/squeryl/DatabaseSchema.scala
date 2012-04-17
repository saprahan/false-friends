package squeryl


import org.squeryl.{Schema, KeyedEntity}
import java.sql.Timestamp


trait DatabaseSchema extends Schema {
  val languages = table[Language]
  val friends = table[Friend]
  val friendWords = table[FriendWord]
}

object DatabaseSchema extends DatabaseSchema


case class Friend(
                   text: String,
                   createdate: Timestamp
                     ) extends KeyedEntity[Long] {
  val id:Long = 0

  def typedId = FriendId(id)
}

case class FriendWord(
                       friendId: Long,
                       languageId: Long, 
                       word: String
                   ) extends KeyedEntity[Long] {
  val id:Long = 0
  
  def typedId = FriendWordId(id)
  
  def typedLanguageId = LanguageId(languageId)
}

case class LanguageId(id: Long) extends LongTypedId
case class FriendId(id: Long) extends LongTypedId
case class FriendWordId(id: Long) extends LongTypedId

trait TypedId[T] {
  def id:T
}

trait LongTypedId extends TypedId[Long]