ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.12"

val spark_version = "2.4.5"
val spark_streaming_kafka_version = "2.4.5"
val spark_cassandra_connector_version = "2.4.3"
val lift_json_version = "3.4.3"
val scalatest_version = "3.0.8"
val scalamock_version = "4.4.0"
val mockito_scala_plugin_version = "1.16.37"
val scalatest_plugin_version = "1.0"

libraryDependencies += "org.apache.spark" % "spark-core_2.12" % spark_version
libraryDependencies += "org.apache.spark" % "spark-streaming_2.12" % spark_version
libraryDependencies += "org.apache.spark" % "spark-sql_2.12" % spark_version
libraryDependencies += "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % spark_version

libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.12" % spark_cassandra_connector_version

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.12" % "2.4.5"
libraryDependencies += "org.apache.spark" % "spark-hive_2.12" % spark_version

libraryDependencies += "net.liftweb" % "lift-json_2.12" % lift_json_version
//% "provided"

libraryDependencies += "org.scalatest" % "scalatest_3" % "3.2.16" % "test"

libraryDependencies += "com.holdenkarau" % "spark-testing-base_2.13" % "3.4.0_1.4.3" % "test"

libraryDependencies += "org.postgresql" % "postgresql" % "42.3.1"


lazy val root = (project in file("."))
  .settings(
    name := "spark_snippet",
    idePackagePrefix := Some("bip.spark.snippet")
  )

