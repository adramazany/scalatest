import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka010.ConsumerStrategies
import org.apache.spark.streaming.kafka010.HasOffsetRanges
import org.apache.spark.streaming.kafka010.LocationStrategies
import org.apache.spark.streaming.kafka010.OffsetRange

object KafkaDirectStream {
  def main(args: Array[String]):Unit = {
    println("KafkaDirectStream starting ...")

    val sparkConf = new SparkConf().setAppName("TestSparkApp").setMaster("local[*]")
    val streamingContext = new StreamingContext(sparkConf, Seconds(10))

//    "bootstrap.servers" -> "localhost:9092,localhost:9092",
//    "auto.offset.reset" -> "latest",
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

//    val topics = Array("LotChangedEvent", "StateChangedEvent")
    val topics = Array("LotChangedEvent")
//    val stream = KafkaUtils.createDirectStream[String, String](
//      streamingContext,
//      org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent,
//      org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe[String,String](topics, kafkaParams)
//    )

    // begin from the offsets committed to the database
//    val fromOffsets : Map[TopicPartition, Long] = Map()
    val fromOffsets =  Map(new TopicPartition("LotChangedEvent", 0) -> 0L)
    val stream = KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent, ConsumerStrategies.Assign[String, String](fromOffsets.keySet, kafkaParams, fromOffsets))
    stream.foreachRDD((rdd) => {
//      val offsetRanges: Array[Nothing] = (rdd.asInstanceOf[Nothing]).offsetRanges
//      val results: AnyRef = yourCalculation(rdd)
      println("YYYY")
      rdd.collect().foreach(println)

    })

    stream.print()

    val s2 = stream.map(s=>s.value())
    println("stream.count()="+s2.count()+"\n")
//    val mds : org.apache.spark.streaming.dstream.MappedDStream = stream.count()

//    stream.foreachRDD(rdd=>
//        println("1",rdd)
//    )

//    val offsetRanges = Array(OffsetRange.create("LotChangedEvent", 0, 0, 100))
//    val rdd = KafkaUtils.createRDD(sparkContext, kafkaParams, offsetRanges, LocationStrategies.PreferConsistent)
//    val rdd = KafkaUtils.createRDD[String, String](streamingContext, kafkaParams, offsetRanges, LocationStrategies.PreferConsistent)

    println()

//    stream.foreachRDD { rdd =>
//      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      rdd.foreachPartition { iter =>
//        val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
//        println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
//      }
//    }

    println("KafkaDirectStream end.")
  }
}
