package bip.kafka.seqtopics

import bip.kafka.seqtopics.biz.LotChangedEventFunctions
import bip.kafka.seqtopics.biz.OperatorLogEventFuncs
import bip.kafka.seqtopics.biz.ProductionResultEventFuncs
import bip.kafka.seqtopics.biz.RecipeChangedEventFuncs
import bip.kafka.seqtopics.biz.StateChangedEventFuncs
import bip.kafka.seqtopics.domain.LotChangedEvent
import bip.kafka.seqtopics.domain.OperatorLogEvent
import bip.kafka.seqtopics.domain.ProductionResultEvent
import bip.kafka.seqtopics.domain.RecipeChangedEvent
import bip.kafka.seqtopics.domain.StateChangedEvent
import bip.kafka.seqtopics.infra.kafkaFunctions
import bip.kafka.seqtopics.infra.sparkFunctions
import bip.kafka.seqtopics.infra.sparkFunctions.ssc
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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Main {
  private final val logger: Logger = LoggerFactory.getLogger(kafkaFunctions.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("Hello Kafka read topics sequentially!")

    val topics = Array("LotChangedEvent", "RecipeChangedEvent", "StateChangedEvent", "OperatorLogEvent", "ProductionResultEvent")
//    val topics = Array("LotChangedEvent", "RecipeChangedEvent")
//    kafkaFunctions.readKafka(topics)
//    kafkaFunctions.readKafka1Topic[LotChangedEvent](topics, LotChangedEventFunctions.apply)
/*
    kafkaFunctions.readKafka2Topics[LotChangedEvent, RecipeChangedEvent](topics,
      LotChangedEventFunctions.apply,
      RecipeChangedEventFuncs.apply
    )
*/
    kafkaFunctions.readKafka5Topics[LotChangedEvent, RecipeChangedEvent, StateChangedEvent, OperatorLogEvent, ProductionResultEvent](topics,
      LotChangedEventFunctions.apply,
      RecipeChangedEventFuncs.apply,
      StateChangedEventFuncs.apply,
      OperatorLogEventFuncs.apply,
      ProductionResultEventFuncs.apply
    )

    sparkFunctions.ssc.start()
    sparkFunctions.ssc.awaitTermination()

  }
}