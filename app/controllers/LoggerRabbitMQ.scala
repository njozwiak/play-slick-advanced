package controllers

import play.api.libs.concurrent.Akka
import akka.actor.Props
import com.rabbitmq.client.Channel
import scala.concurrent.duration._
import play.api.Play.current
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

object LoggerRabbitMQ {

  def initQueue = {
    // create the connection
    val connection = RabbitMQConnection.getConnection()
    // create the channel we use to send
    val sendingChannel = connection.createChannel()
    // make sure the queue exists we want to send to
    sendingChannel.queueDeclare(Config.RABBITMQ_QUEUE, false, false, false, null)

    new SendingMessage(channel = sendingChannel, queue = Config.RABBITMQ_QUEUE, "Test message sent")
  }

  def setupListener(receivingChannel: Channel, queue: String) {
    Akka.system.scheduler.scheduleOnce(2 seconds,
                                       Akka.system.actorOf(Props(new ListeningActor(receivingChannel, queue))), "")
  }

}
