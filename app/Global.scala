import controllers.{Config, LoggerRabbitMQ}
import play.api.{Logger, Application, GlobalSettings}

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")
    LoggerRabbitMQ.initQueue
    Config.initConsoleReporter
  }
}