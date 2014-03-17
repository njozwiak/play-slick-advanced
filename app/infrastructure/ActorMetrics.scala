package infrastructure

import akka.actor.Actor
import com.codahale.metrics.health.HealthCheckRegistry

trait ActorMetrics extends Actor with Instrumented {

  def receiveActor: Receive

  def receive: Receive = {
    val loading = metrics.timer("message-handler")
    loading.time {
      receiveActor
    }
  }

  override val registry: HealthCheckRegistry = Config.healthCheckRegistry
}
