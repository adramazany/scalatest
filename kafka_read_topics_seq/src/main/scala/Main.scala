package bip.kafka.seqtopics

import bip.kafka.seqtopics.infra.kafkaFunctions
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.Seconds
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.rdd.RDD
import scala.collection.mutable.Queue
import org.apache.kafka.clients.consumer.ConsumerRecord

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello Kafka read topics sequantialy!")

    val topics = Array("LotChangedEvent", "RecipeChangedEvent", "StateChangedEvent", "OperatorLogEvent", "ProductionResultEvent")
    kafkaFunctions.readKafka(topics)
  }
}