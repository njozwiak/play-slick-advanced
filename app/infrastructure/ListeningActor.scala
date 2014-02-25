package infrastructure

import com.rabbitmq.client.{QueueingConsumer, Channel}
import akka.actor.Actor
import play.api.Logger

class ListeningActor(channel: Channel, queue: String) extends Actor {

  def receive = {
    case _ =>
      val consumer = new QueueingConsumer(channel)
      channel.basicConsume(queue, true, consumer)

      val delivery = consumer.nextDelivery()
      val msg = new String(delivery.getBody)

      Logger.info("Received message : " + msg)
  }
}