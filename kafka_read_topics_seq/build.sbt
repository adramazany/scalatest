ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.12.10"

val spark_version = "2.4.5"
val spark_streaming_kafka_version = "2.4.5"
val spark_cassandra_connector_version = "2.4.3"
val lift_json_version = "3.4.3"
val scalatest_version = "3.0.8"
val scalamock_version = "4.4.0"
val mockito_scala_plugin_version = "1.16.37"
val scalatest_plugin_version = "1.0"

lazy val kafka_read_topics_seq = project
  .in(file("."))
  .settings(
    name := "kafka_read_topics_seq",
    idePackagePrefix := Some("bip.kafka.seqtopics"),
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "2.4.5",
      "org.apache.spark" %% "spark-streaming" % spark_version,
      "org.apache.spark" %% "spark-sql" % spark_version,
      "org.apache.spark" %% "spark-sql-kafka-0-10" % spark_version,
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % spark_version,
      "org.apache.spark" %% "spark-hive" % spark_version,
      "com.datastax.spark" %% "spark-cassandra-connector" % spark_cassandra_connector_version,
      "net.liftweb" %% "lift-json" % lift_json_version,
      "org.postgresql" % "postgresql" % "42.3.1"
    )
  )
