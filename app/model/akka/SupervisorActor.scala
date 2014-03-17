package model.akka

import scala.concurrent.duration._
import akka.actor.{Props, OneForOneStrategy, ActorLogging}
import akka.actor.SupervisorStrategy.{Escalate, Stop, Restart, Resume}
import infrastructure.ActorMetrics

class SupervisorActor extends ActorMetrics with ActorLogging {

  val workerActor = context.actorOf(Props[WorkerActor], name = "workerActor")
  val workerActor2 = context.actorOf(Props[WorkerActor], name = "workerActor2")

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  def receiveActor = {
    case result:Result =>
      workerActor.tell(result, sender)
      //childActor ! result
    case msg: Object =>
      workerActor ! msg
  }
}
