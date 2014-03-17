package model.akka

import akka.actor.{ActorLogging, Actor}
import infrastructure.ActorMetrics

class WorkerActor extends ActorMetrics with ActorLogging{
  var state: Int = 0

  override def preStart() {
    log.info("Starting WorkerActor instance hashcode # {}", this.hashCode())
  }
  override def postStop() {
    log.info("Stopping WorkerActor instance hashcode # {}", this.hashCode())
  }
  override def receiveActor: Receive = {
    case value: Int =>
      if (value <= 0)
        throw new ArithmeticException("Number equal or less than zero")
      else
        state = value
    case result: Result =>
      sender ! state
    case ex: NullPointerException =>
      throw new NullPointerException("Null Value Passed")
    case _ =>
      throw new IllegalArgumentException("Wrong Argument")
  }
}
