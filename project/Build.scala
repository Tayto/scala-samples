import sbt._
import sbt.Keys._
import sbt.project
import sbt.Plugins._

import sbt.project

import sbt._
import Keys._

//import ScalaJSPlugin._
//import ScalaJSPlugin.autoImport._

object BuildProject extends Build {

  val akkaDependencies = Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
    "com.typesafe.akka" % "akka-stream_2.11" % "2.4.2"
  )

  val scalazDependencies = Seq(
    "org.scalaz" %% "scalaz-core" % "7.1.0",
    "org.scalaz" %% "scalaz-effect" % "7.1.0",
    "org.scalaz" %% "scalaz-typelevel" % "7.1.0",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.1.0" % "test"
  )

  val coreDependencies = Seq(
    "org.specs2" %% "specs2" % "2.3.11" % "test",
    "org.scalatest" %% "scalatest" % "2.1.5",
    "joda-time" % "joda-time" % "2.8.2",
    "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
    "org.slf4j" % "slf4j-log4j12" % "1.5.2",
    "ch.qos.logback" % "logback-classic" % "1.0.9",
    "com.storm-enroute" %% "scalameter" % "0.7",
    "com.typesafe.play" % "play-json_2.11" % "2.3.10",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.1"
  )

  val scalaRxDependencies =  Seq(
    "com.scalarx" % "scalarx_2.11" % "0.3.1"
  )


  lazy val lang = Project("Lang", file("lang")) settings(
    version       := "0.5",
    scalaVersion  := "2.11.7",

    scalacOptions in Test ++= Seq("-Yrangepos"),
    scalacOptions += "-feature",

    initialCommands in console := "import scalaz._, Scalaz._",

    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),

    resolvers ++= Seq(
      "Repo1" at "http://oss.sonatype.org/content/repositories/releases",
      "Repo2" at "http://repo1.maven.org/maven2"
    ),

    //resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

    libraryDependencies ++= coreDependencies,
    libraryDependencies ++= akkaDependencies,
    libraryDependencies ++= scalazDependencies
  )

  lazy val web = Project(id = "Web", base = file("web")) settings(
    version       := "0.1",
    scalaVersion  := "2.11.7",

    libraryDependencies ++= coreDependencies
  )

  lazy val akka = Project(id = "Akka", base = file("akka")) settings(
    version       := "0.1",
    scalaVersion  := "2.11.7",

    libraryDependencies ++= coreDependencies,
    libraryDependencies ++= akkaDependencies
   )

  lazy val ScalaJSPlugin = addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.8")


  lazy val scalaRx = Project(id = "ScalaRX", base = file("scalarx"))/*.enablePlugins(ScalaJSPlugin)*/.settings(
     version      := "0.1",
     scalaVersion := "2.11.7",

     libraryDependencies ++= scalaRxDependencies
    )

  lazy val scalaSamples = Project(id = "ScalaSamples", base = file(".")) aggregate(lang, web, akka, scalaRx)

}