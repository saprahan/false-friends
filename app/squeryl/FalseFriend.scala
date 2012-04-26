package squeryl
import java.sql.Timestamp
import org.squeryl.KeyedEntity
import org.squeryl.{Schema}
import org.squeryl.PrimitiveTypeMode._


case class FalseFriendId(id: Long) extends LongTypedId

case class FalseFriend(
                   text: String,
                   var createdate: Timestamp = null
                     ) extends KeyedEntity[Long] {
  val id:Long = 0

  def typedId = FalseFriendId(id) 
  
  def this() = this(null)
}