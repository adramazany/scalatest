package bip.spark.snippet

/*
// usage:
// spark-shell.cmd --jars C:\Development\.m2\repository\org\apache\spark\spark-streaming-kafka-0-10_2.12\2.4.5\spark-streaming-kafka-0-10_2.12-2.4.5.jar
// :require C:\Development\.m2\repository\org\apache\kafka\kafka-clients\2.0.0\kafka-clients-2.0.0.jar
// :load test-kafka-stream.scala
*/

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
import org.apache.spark.SparkContext

object TestKafkaStream {
  lazy val conf = new SparkConf().setAppName("Problem1")
  lazy val sc = new SparkContext(conf)

val kafkaParams = Map[String, Object](
  "bootstrap.servers" -> "localhost:9092",
  "key.deserializer" -> classOf[StringDeserializer],
  "value.deserializer" -> classOf[StringDeserializer],
  "group.id" -> "use_a_separate_group_id_for_each_stream",
  "auto.offset.reset" -> "latest",
  "enable.auto.commit" -> (false: java.lang.Boolean)
)

val topics = Array("LotChangedEvent", "RecipeChangedEvent", "StateChangedEvent", "OperatorLogEvent", "ProductionResultEvent")

//ssc.stop()

val ssc = new StreamingContext(sc, Seconds(2))
val stream = KafkaUtils.createDirectStream[String, String](ssc,PreferConsistent,Subscribe[String, String](topics, kafkaParams))

stream.foreachRDD( rdd => {
//  val l = rdd.filter(r => !r.topic().isEmpty).map(_).repartition(1)(Ordering.by(_.timestamp())).foreach(cr => println(cr.timestamp(), cr.topic()))
//   val l = rdd.filter(r => !r.topic().isEmpty).map((_.timestamp(),)).repartition(1).sortBy(_.toString).foreach(cr => println(cr))
  rdd.filter(r => !r.topic().isEmpty)
    .map(kcr => (kcr.timestamp(), kcr.topic(), kcr.key(), kcr.value()))
    .repartition(1)(Ordering.by(_._1))
    .sortBy(_._1)
    .foreach(cr => println(cr))
})

ssc.start
//ssc.awaitTermination
//ssc.stop()
}