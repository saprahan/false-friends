package service
import squeryl.CRUDService
import squeryl.Language
import squeryl.LanguageId
import org.squeryl.PrimitiveTypeMode._
import squeryl.DatabaseSchema

trait GenericLanguageServiceComponent {
  def languageService: LanguageService

  trait LanguageService extends CRUDService[Language, LanguageId] {
    def selectAll: Seq[Language]
  }
}

trait LanguageServiceComponent extends GenericLanguageServiceComponent {
  val languageService = new LanguageServiceImpl

  class LanguageServiceImpl extends LanguageService with DatabaseSchema {
    def create(entity: Language) = inTransaction {
      languages.insert(entity).typedId
    }

    def update(entity: Language) = inTransaction {
      languages.update(entity)
    }

    def lookup(entityId: LanguageId) = inTransaction {
      languages.lookup(entityId.id)
    }

    def delete(entityId: LanguageId) = inTransaction {
      languages.delete(entityId.id)
    }

    def selectAll = inTransaction {
      from(languages)(l =>
        select(l)
          orderBy (l.code)).toSeq
    }
  }
}