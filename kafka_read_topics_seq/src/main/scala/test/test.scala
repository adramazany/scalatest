package bip.kafka.seqtopics
package test

/**
 * @author ramezania
 */
/*
// usage:
// spark-shell.cmd --jars C:\Development\.m2\repository\org\apache\spark\spark-streaming-kafka-0-10_2.12\2.4.5\spark-streaming-kafka-0-10_2.12-2.4.5.jar
// :require C:\Development\.m2\repository\org\apache\kafka\kafka-clients\2.0.0\kafka-clients-2.0.0.jar
// :load test-kafka-stream.scala
*/

import de.muehlbauer.factoryleader.dataprocessing.lib.streamevent.LotChangedEvent
import net.liftweb.json.parse
import net.liftweb.json.DefaultFormats
import org.apache.kafka.clients.consumer.ConsumerRecord
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
import org.json4s.Formats
import scala.collection.mutable.Queue
import scala.reflect.Manifest
import scala.util.Try
import scala.reflect.runtime.universe

object test {

  var kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "use_a_separate_group_id_for_each_stream",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  case class D1(id:  Int, name: String)
  val json = """{"id":1, "name":"object 1"}"""

  case class D2(id:  Int)

//  net.liftweb.json.parse(json).extract[Manifest[D1]]

  case class LotChangedEvent1(
    machineId:  String,
    changeType: String,
    name:       String,
    id:         String,
    `type`:     Option[String],
    size:       Option[Long],
    unit:       Option[String],
    timestamp:  Long,
    parameters: Option[Map[String, String]])

  def applyLotChangedEvent(event: LotChangedEvent1): Unit = {
    println(event.timestamp)
  }

  def ext(json:String,c: String):Any={
    implicit val formats = net.liftweb.json.DefaultFormats
//    net.liftweb.json.parse(json).extract[Class.forName(manClassName)]
//    val n = classOf[D1].getName
//    val n = classOf[D1].getTypeName
//    val n = classOf[D1].getSimpleName
//    val n = classOf[D1].getCanonicalName
//    net.liftweb.json.parse(json).extract[Class.for]
  }

  object Inst {

    def apply(
      className: String,
      arg:       Any) = {
      val runtimeMirror: universe.Mirror = universe.runtimeMirror(getClass.getClassLoader)
      val classSymbol: universe.ClassSymbol = runtimeMirror.classSymbol(Class.forName(className))
      val classMirror: universe.ClassMirror = runtimeMirror.reflectClass(classSymbol)
      if (classSymbol.companion.toString() == "<none>") // TODO: use nicer method "hiding" in the api?
      {
        println(s"Info: $className has no companion object")
        val constructors = classSymbol.typeSignature.members.filter(_.isConstructor).toList
        if (constructors.length > 1) {
          println(s"Info: $className has several constructors")
        }
        else {
          val constructorMirror = classMirror.reflectConstructor(constructors.head.asMethod) // we can reuse it
          constructorMirror()
        }
      }
      else {
        val companionSymbol = classSymbol.companion
        println(s"Info: $className has companion object $companionSymbol")
        // TBD
      }
    }
  }




  var topics = Array("LotChangedEvent", "RecipeChangedEvent", "StateChangedEvent", "OperatorLogEvent", "ProductionResultEvent")
//  var topicsDetail = Map("LotChangedEvent" -> (LotChangedEvent1.getClass, (event: LotChangedEvent1) => applyLotChangedEvent(event))
//  var topicsDetail = Map("LotChangedEvent" -> (LotChangedEvent1.getClass[_], LotChangedEvent1.asInstanceOf, (event: LotChangedEvent1) => applyLotChangedEvent(event))
//    , "RecipeChangedEvent" -> (None, None))

  val sc = new SparkConf()
    .setMaster("local[*]")
    .setAppName("test")

  // ssc.stop()
  val ssc = new StreamingContext(sc, Seconds(2))
  var stream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent, Subscribe[String, String](topics, kafkaParams))

  stream.foreachRDD(rdd => {
    rdd.filter(r => !r.topic().isEmpty)
      .map(kcr => (kcr.timestamp(), kcr.topic(), kcr.key(), kcr.value()))
      .repartition(1)(Ordering.by(_._1))
      .sortBy(_._1)
      .foreach(cr => {
        println(cr)
        val topic = cr._2
        val json = cr._4
//        val detail = topicsDetail.get(topic)
//        if (detail.isDefined) {
//          val STREAMEVENT = detail.get._1
//          implicit val formats = net.liftweb.json.DefaultFormats
//            //      val input = parse(json).extract[LotChangedEvent1]
//          val input = parse(json).extract[STREAMEVENT.type]
//          println(input)
//        }
      })
  })

//  def xyz[X,Y,Z](json:String,x:X,y:Y,z:Z): Unit = {
//    implicit val formats = DefaultFormats
//    val v1 = parse(json).extract[X]
//    val v2 = Try(parse(json).extract[Y])
//  }

  ssc.start
//  ssc.awaitTermination
//ssc.stop

//  def readKafka[A,B,C,D,E](Map[String,callback (A)]):
val t1 = ("a"->"AAA", "b"->"BBB", "c"->"CCC")
  t1._2._1


  val f:Function1[String,Unit]= a => print("f",a)
  val l=List(f)
  print(l(0))
  l(0)("xxxxx")

  val l2 = List(D1(1,"AAA"),D1(2,"BBB"))
//  l2 ::


}