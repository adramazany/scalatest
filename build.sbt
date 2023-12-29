ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.12.10"

lazy val spark_snippet = (project in file("spark_snippet"))
lazy val kafka_read_topics_seq = (project in file("kafka_read_topics_seq"))

lazy val root = (project in file("."))
  .aggregate(spark_snippet, kafka_read_topics_seq)
  .settings(
    name := "scalatest",
    idePackagePrefix := Some("bip.scalatest")
    /*,update / aggregate := false*/
  )

// extra
//lazy val core = project.dependsOn(util)
//dependsOn(util % "compile->compile")