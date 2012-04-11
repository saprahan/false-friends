package squeryl


import org.squeryl.{Schema, KeyedEntity}
import java.sql.Timestamp


object DatabaseSchema extends Schema {
  val languages = table[Language]
  val friends = table[Friend]
  val friendWords = table[FriendWord]
}

case class Language(
    name: String, 
    nameEnglish: String, 
    code: String
                     ) extends KeyedEntity[Long] {
  val id:Long = 0

  def typedId = LanguageId(id)
}

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

case class LanguageId(id: Long)
case class FriendId(id: Long)
case class FriendWordId(id: Long)