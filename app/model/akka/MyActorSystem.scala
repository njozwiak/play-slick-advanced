package model.akka

import akka.actor.{ActorRef, DeadLetter, ActorSystem, Props}
import scala.concurrent.Future
import akka.pattern.Patterns
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit
import akka.util.Timeout
import infrastructure.Config

case class Result()

object MyActorSystem {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("faultTolerance")
    val log = system.log

    val supervisor = system.actorOf(Props[SupervisorActor], name = "supervisor")
    val listenerActor = system.actorOf(Props(classOf[ListenerActor]), name = "listenerActor")

    system.eventStream.subscribe(listenerActor, classOf[DeadLetter])

    log.info("Sending value 8, no exception")
    var message = 8
    supervisor ! message

    var result: Future[Int] = Patterns.ask(supervisor, new Result, 5000).mapTo[Int]

    log.info("Value Received-> {}", result.value)

    log.info("Sending value -8, ArithmeticException should be thrown! Our Supervisor strategy says resume !")
    message = -8
    supervisor ! message

    result = Patterns.ask(supervisor, new Result, 5000).mapTo[Int]

    log.info("Value Received-> {}", result.value)

    log.info("NullPointerException should be thrown! Our Supervisor strategy says restart !")
    supervisor ! new NullPointerException

    log.info("IllegalArgumentException should be thrown! Our Supervisor strategy says stop !")

    supervisor ! new IllegalArgumentException

    system.shutdown()
  }
}
