import org.squeryl.adapters.H2Adapter
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.Session
import org.squeryl.SessionFactory

import play.api.Play.current
import play.api.db.DB
import play.api.db.DB
import play.api.mvc.Handler
import play.api.mvc.RequestHeader
import play.api.Application
import play.api.GlobalSettings

object Global extends GlobalSettings {
  override def onStart(app: Application): Unit =
    {
      SessionFactory.externalTransactionManagementAdapter = Some(() =>
        Some(new Session(
          DB.getConnection("default", true),
          dbAdapter)))
    }

  val dbAdapter = new H2Adapter();

}
