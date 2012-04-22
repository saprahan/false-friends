package squeryl
import org.squeryl.KeyedEntity

trait CRUDService[A <: KeyedEntity[Long],B] {
	def create(entity:A):B
	def update(entity:A)
	def lookup(entityId:B):Option[A]
	def delete(entityId:B)
}