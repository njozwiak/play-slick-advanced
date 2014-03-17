package model.akka

import akka.actor.{DeadLetter, Actor}

class ListenerActor extends Actor {

  def receive: Receive = {
    case d:DeadLetter => println("**********************************************")
  }
}
