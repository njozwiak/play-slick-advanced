name := """play-slick-advanced"""

version := "1.0.1"

scalaVersion := "2.10.3"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  // Select Play modules
  //jdbc,      // The JDBC connection pool and the play.api.db API
  //anorm,     // Scala RDBMS Library
  //javaJdbc,  // Java database API
  //javaEbean, // Java Ebean plugin
  //javaJpa,   // Java JPA plugin
  //filters,   // A set of built-in filters
  javaCore,  // The core Java API
  // WebJars pull in client-side web libraries
  "org.webjars" %% "webjars-play" % "2.2.1",
  "org.webjars" % "bootstrap" % "2.3.2",
  "com.typesafe.slick" %% "slick" % "1.0.1",
  "com.typesafe.play" %% "play-slick" % "0.5.0.8",
  "org.virtuslab" %% "unicorn" % "0.4",
  "com.codahale.metrics" % "metrics-core" % "3.0.1",
  "com.codahale.metrics" % "metrics-graphite" % "3.0.1",
  "nl.grons" %% "metrics-scala" % "3.0.4",
  "com.rabbitmq" % "amqp-client" % "2.8.1",
  "com.typesafe.akka" %% "akka-actor" % "2.3.0",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.0-RC4" % "test",
  "org.scalatest" %% "scalatest" % "2.1.0" % "test"
  // Add your own project dependencies in the form:
  // "group" % "artifact" % "version"
)

play.Project.playScalaSettings
