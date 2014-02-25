package controllers

import com.rabbitmq.client.Channel
import play.api.Logger

class SendingMessage(channel: Channel, queue: String, text: String) {

  val msg = text + " : " + System.currentTimeMillis()
  channel.basicPublish("", queue, null, msg.getBytes)
  Logger.info(msg)
}
