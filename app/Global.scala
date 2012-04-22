import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, SessionFactory}
import play.api.db.DB
import play.api.GlobalSettings
import play.api.Application
import play.api.db.DB
import com.google.inject.Guice
import play.api.mvc.RequestHeader
import play.api.mvc.Handler
import play.api.Play.current
import org.squeryl.adapters.MySQLAdapter

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
