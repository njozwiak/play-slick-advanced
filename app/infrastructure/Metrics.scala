package infrastructure

import com.codahale.metrics.health.HealthCheckRegistry

trait Metrics extends ActorStack with Instrumented {

  override def receive:Receive = {
    case value =>
      val loading = metrics.timer("message-actor")
      loading.time {
       super.receive(value)
     }
  }

  override val registry: HealthCheckRegistry = Config.healthCheckRegistry

}
