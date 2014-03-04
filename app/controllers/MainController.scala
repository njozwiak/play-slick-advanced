package controllers

import play.api.mvc.{Action, Controller}
import play.api.db.slick._
import play.api.libs.json._
import play.api.Play.current
import service.UsersService
import model.{UserId, User}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import com.codahale.metrics.health.HealthCheckRegistry
import infrastructure.{LoggerRabbitMQ, RabbitMQConnection, Instrumented, Config}
import play.api.data.validation.ValidationError

object MainController extends Controller with Instrumented {

  def index = Action {
    Redirect(routes.MainController.users)
  }

  implicit val userIdWrite = Json.writes[UserId]
  implicit val userWrite = Json.writes[User]

  implicit val userIdRead = Json.reads[UserId]

  def lastNameReads(implicit r: Reads[String]): Reads[String] = r.filter(ValidationError("error.lastName.numbers"))(_.matches("\\S+\\d{4}$"))

  implicit val userRead: Reads[User] = {(
    (__ \ "id").readNullable[UserId] and
    (__ \ "email").read(email keepAnd minLength[String](5)) and
    (__ \ "firstName").read(minLength[String](2) andKeep maxLength[String](30)) and
    (__ \ "lastName").read(lastNameReads) and
    (__ \ "dateAccess").read(jodaLocalDateReads("yyyyMMdd"))
    )(User)
  }

  def users = DBAction {
   implicit rs =>
     val loading = metrics.timer("loading Find All Users")
     loading.time {
      val connection = RabbitMQConnection.getConnection()
      LoggerRabbitMQ.setupListener(connection.createChannel(), Config.RABBITMQ_QUEUE)

      Ok(Json.toJson(UsersService.findAll)).as("application/json")
     }
  }

  def user(id: Long) = DBAction {
    implicit rs =>
      Ok(Json.toJson(UsersService findById UserId(id))).as("application/json")
  }

  def newUser = DBAction(parse.json) {
   implicit rs =>
     rs.request.body.validate[User].map {
        case user =>
          UsersService save User(None, user.email, user.firstName, user.lastName, user.dateAccess)
          Created("User Created")
      }.recoverTotal {
       e => NotFound("Detected error:" + JsError.toFlatJson(e))
      }
  }

  def deleteUser(id: Long) = DBAction {
    implicit rs =>
      try {
        UsersService deleteById UserId(id)
        Redirect(routes.MainController.users)
      } catch {
        case _:Throwable => {
          NotFound("Error while deleting user")
        }
      }
  }

  override val registry: HealthCheckRegistry = Config.healthCheckRegistry
}