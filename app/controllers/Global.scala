import play.api._
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import play.db.DB
import org.squeryl.Session

object Global extends GlobalSettings {

  val dbAdapter = new H2Adapter();

  override def onStart(app: Application) {
    Logger.info("Application has sdfsfsfsdfsfsfsdfsdf")
    SessionFactory.concreteFactory = Some(
      () => Session.create(DB.getDataSource().getConnection(),
        dbAdapter));
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

}