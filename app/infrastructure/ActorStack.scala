package infrastructure

import akka.actor.Actor

trait ActorStack extends Actor {

   def receiveActor:Receive

   def receive:Receive = {
     case value => receiveActor(value)
   }

 }
