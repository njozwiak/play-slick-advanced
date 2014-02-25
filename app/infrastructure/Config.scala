package infrastructure

import com.typesafe.config.ConfigFactory
import com.codahale.metrics.ConsoleReporter
import java.util.concurrent.TimeUnit
import nl.grons.metrics.scala.{CheckedBuilder, InstrumentedBuilder}

object Config {

  val RABBITMQ_HOST = ConfigFactory.load().getString("rabbitmq.host")
  val RABBITMQ_QUEUE = ConfigFactory.load().getString("rabbitmq.queue")
  val RABBITMQ_EXCHANGEE = ConfigFactory.load().getString("rabbitmq.exchange")

  val metricRegistry = new com.codahale.metrics.MetricRegistry()

  val healthCheckRegistry = new com.codahale.metrics.health.HealthCheckRegistry()

  def initConsoleReporter = ConsoleReporter.forRegistry(metricRegistry)
                                           .convertRatesTo(TimeUnit.SECONDS)
                                           .convertDurationsTo(TimeUnit.MILLISECONDS)
                                           .build()
                                           .start(1, TimeUnit.MINUTES)

  /*val graphite: Graphite = new Graphite(new InetSocketAddress("vgraph01.adm.adencf.local", 2003))
  GraphiteReporter.forRegistry(metricRegistry)
                  .prefixedWith("jvm.dev.njozwiak.playSlickScala")
                  .convertRatesTo(TimeUnit.SECONDS)
                  .convertDurationsTo(TimeUnit.MILLISECONDS)
                  .filter(MetricFilter.ALL)
                  .build(graphite)
                  .start(1, TimeUnit.MINUTES)*/
}

trait Instrumented extends InstrumentedBuilder with CheckedBuilder {
  val metricRegistry = Config.metricRegistry
  val healthCheckRegistry = Config.healthCheckRegistry
}

