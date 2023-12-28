package bip.kafka.seqtopics
package infra

import bip.kafka.seqtopics.conf.kafkaConfiguration
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.reflect.runtime.universe.typeOf
import scala.util.Try

/**
 * @author ramezania
 */
object kafkaFunctions {
  private final val logger: Logger = LoggerFactory.getLogger(kafkaFunctions.getClass)

  implicit val formats = net.liftweb.json.DefaultFormats

  def readKafka1Topic[A:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit): Unit = {
    logger.info("readKafka1Topic started for topics: %s".format(topics.toList))
    readKafkaNTopics[A, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty](1, topics, callbackA, null, null, null, null, null, null, null, null, null)
  }

  def readKafka2Topics[A:Manifest, B:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit): Unit = {
    logger.info("readKafka2Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty](2, topics, callbackA, callbackB, null, null, null, null, null, null, null, null)
  }

  def readKafka3Topics[A:Manifest, B:Manifest, C:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit): Unit = {
    logger.info("readKafka3Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C, Empty, Empty, Empty, Empty, Empty, Empty, Empty](3, topics, callbackA, callbackB, callbackC, null, null, null, null, null, null, null)
  }

  def readKafka4Topics[A:Manifest, B:Manifest, C:Manifest, D:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit, callbackD: (D)=>Unit): Unit = {
    logger.info("readKafka4Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C,D, Empty, Empty, Empty, Empty, Empty, Empty](4, topics, callbackA, callbackB, callbackC, callbackD, null, null, null, null, null, null)
  }

  def readKafka5Topics[A:Manifest, B:Manifest, C:Manifest, D:Manifest, E:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit, callbackD: (D)=>Unit, callbackE: (E)=>Unit): Unit = {
    logger.info("readKafka5Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C,D,E, Empty, Empty, Empty, Empty, Empty](5, topics, callbackA, callbackB, callbackC, callbackD, callbackE, null, null, null, null, null)
  }

  def readKafka6Topics[A:Manifest, B:Manifest, C:Manifest, D:Manifest, E:Manifest, F:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit, callbackD: (D)=>Unit, callbackE: (E)=>Unit, callbackF: (F)=>Unit): Unit = {
    logger.info("readKafka6Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C,D,E,F, Empty, Empty, Empty, Empty](6, topics, callbackA, callbackB, callbackC, callbackD, callbackE, callbackF, null, null, null, null)
  }

  def readKafka7Topics[A:Manifest, B:Manifest, C:Manifest, D:Manifest, E:Manifest, F:Manifest, G:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit, callbackD: (D)=>Unit, callbackE: (E)=>Unit, callbackF: (F)=>Unit, callbackG: (G)=>Unit): Unit = {
    logger.info("readKafka7Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C,D,E,F,G, Empty, Empty, Empty](7, topics, callbackA, callbackB, callbackC, callbackD, callbackE, callbackF, callbackG, null, null, null)
  }

  def readKafka8Topics[A:Manifest, B:Manifest, C:Manifest, D:Manifest, E:Manifest, F:Manifest, G:Manifest, H:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit, callbackD: (D)=>Unit, callbackE: (E)=>Unit, callbackF: (F)=>Unit, callbackG: (G)=>Unit, callbackH: (H)=>Unit): Unit = {
    logger.info("readKafka8Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C,D,E,F,G,H, Empty, Empty](8, topics, callbackA, callbackB, callbackC, callbackD, callbackE, callbackF, callbackG, callbackH, null, null)
  }

  def readKafka9Topics[A:Manifest, B:Manifest, C:Manifest, D:Manifest, E:Manifest, F:Manifest, G:Manifest, H:Manifest, I:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit, callbackD: (D)=>Unit, callbackE: (E)=>Unit, callbackF: (F)=>Unit, callbackG: (G)=>Unit, callbackH: (H)=>Unit, callbackI: (I)=>Unit): Unit = {
    logger.info("readKafka9Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C,D,E,F,G,H,I, Empty](9, topics, callbackA, callbackB, callbackC, callbackD, callbackE, callbackF, callbackG, callbackH, callbackI, null)
  }

  def readKafka10Topics[A:Manifest, B:Manifest, C:Manifest, D:Manifest, E:Manifest, F:Manifest, G:Manifest, H:Manifest, I:Manifest, J:Manifest]
  (topics:    Array[String], callbackA: (A) => Unit, callbackB: (B) => Unit, callbackC: (C)=>Unit, callbackD: (D)=>Unit, callbackE: (E)=>Unit, callbackF: (F)=>Unit, callbackG: (G)=>Unit, callbackH: (H)=>Unit, callbackI: (I)=>Unit, callbackJ: (J)=>Unit ): Unit = {
    logger.info("readKafka10Topics started for topics: %s".format(topics.toList))
    readKafkaNTopics[A,B,C,D,E,F,G,H,I,J](10, topics, callbackA, callbackB, callbackC, callbackD, callbackE, callbackF, callbackG, callbackH, callbackI, callbackJ)
  }

    def readKafkaNTopics[A:Manifest, B:Manifest, C:Manifest, D:Manifest, E:Manifest, F:Manifest, G:Manifest, H:Manifest, I:Manifest, J:Manifest]
  (n: Int, topics: Array[String],
    callbackA: (A)=>Unit,
    callbackB: (B)=>Unit,
    callbackC: (C)=>Unit,
    callbackD: (D)=>Unit,
    callbackE: (E)=>Unit,
    callbackF: (F)=>Unit,
    callbackG: (G)=>Unit,
    callbackH: (H)=>Unit,
    callbackI: (I)=>Unit,
    callbackJ: (J)=>Unit
  ): Unit = {
    logger.debug("readKafkaNTopics started for topics: %s".format(topics.toList))

    // Important! What you're looking for is a stable identifier. In Scala, these must either start with an uppercase letter, or be surrounded by backticks.
    val TopicA = topics(0)
    val TopicB = if(topics.length>1 && n>1) topics(1) else "UNKNOWN"
    val TopicC = if(topics.length>2 && n>2) topics(2) else "UNKNOWN"
    val TopicD = if(topics.length>3 && n>3) topics(3) else "UNKNOWN"
    val TopicE = if(topics.length>4 && n>4) topics(4) else "UNKNOWN"
    val TopicF = if(topics.length>5 && n>5) topics(5) else "UNKNOWN"
    val TopicG = if(topics.length>6 && n>6) topics(6) else "UNKNOWN"
    val TopicH = if(topics.length>7 && n>7) topics(7) else "UNKNOWN"
    val TopicI = if(topics.length>8 && n>8) topics(8) else "UNKNOWN"
    val TopicJ = if(topics.length>9 && n>9) topics(9) else "UNKNOWN"

    val stream = KafkaUtils.createDirectStream[String, String](sparkFunctions.ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaConfiguration.kafkaParams
        +("group.id" -> generateGroupId(sparkFunctions.applicationName, topics.mkString(",")))))

    stream.foreachRDD(rdd => {
      rdd.filter(r => !r.topic().isEmpty)
        .map(kcr => (kcr.timestamp(), kcr.topic(), kcr.key(), kcr.value()))
        .repartition(1)(Ordering.by(_._1))
        .sortBy(_._1)
        .foreach(cr => {

          val topic: String = cr._2
          val json = cr._4
          logger.debug("readKafkaNTopics: received message of topic:%s is :%s".format(topic, cr))
          var typeOfTopic = ""
          try{
            topic match {
              case TopicA => { typeOfTopic = typeOf[A].toString
                  val obj:A = net.liftweb.json.parse(json).extract[A]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackA(obj) }
              case TopicB => { typeOfTopic = typeOf[B].toString
                  val obj:B = net.liftweb.json.parse(json).extract[B]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackB(obj) }
              case TopicC => { typeOfTopic = typeOf[C].toString
                  val obj:C = net.liftweb.json.parse(json).extract[C]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackC(obj) }
              case TopicD => { typeOfTopic = typeOf[D].toString
                  val obj:D = net.liftweb.json.parse(json).extract[D]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackD(obj) }
              case TopicE => { typeOfTopic = typeOf[E].toString
                  val obj:E = net.liftweb.json.parse(json).extract[E]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackE(obj) }
              case TopicF => { typeOfTopic = typeOf[F].toString
                  val obj:F = net.liftweb.json.parse(json).extract[F]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackF(obj) }
              case TopicG => { typeOfTopic = typeOf[G].toString
                  val obj:G = net.liftweb.json.parse(json).extract[G]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackG(obj) }
              case TopicH => { typeOfTopic = typeOf[H].toString
                  val obj:H = net.liftweb.json.parse(json).extract[H]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackH(obj) }
              case TopicI => { typeOfTopic = typeOf[I].toString
                  val obj:I = net.liftweb.json.parse(json).extract[I]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackI(obj) }
              case TopicJ => { typeOfTopic = typeOf[J].toString
                  val obj:J = net.liftweb.json.parse(json).extract[J]
                  logger.trace("readKafkaNTopics: identified object of type:%s message is :%s".format(typeOfTopic, obj))
                  callbackJ(obj) }
              case _ => logger.error("readKafkaNTopics: Couldn't find a suit topic for received message!,"+ cr)
            }
          } catch {
            case _: Throwable => logger.error("readKafkaNTopics: Couldn't convert the received message to an object of %s: %s".format(typeOfTopic, cr))
          }
        })
    })
  }

  private def generateGroupId(
    streamEventConsumerName: String,
    streamEventName:         String,
    appName:                 Option[String] = None): String = {
    if (appName.isDefined)
      appName.get + "-" + streamEventConsumerName + "-" + streamEventName
    else
      streamEventConsumerName + "-" + streamEventName
  }

  private case class Empty(timestamp:Int)

  /*
  def readKafka1Topic[A: Manifest](topics: Array[String], callbackA: (A) => Unit): Unit = {
    logger.info("readKafka1Topic started for topics: %s".format(topics.toList))

    val stream = KafkaUtils.createDirectStream[String, String](sparkFunctions.ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaConfiguration.kafkaParams
        + ("group.id" -> generateGroupId(sparkFunctions.applicationName, topics.mkString(",")))))

    stream.foreachRDD(rdd => {
      rdd.filter(r => !r.topic().isEmpty)
        .map(kcr => (kcr.timestamp(), kcr.topic(), kcr.key(), kcr.value()))
        .repartition(1)(Ordering.by(_._1))
        .sortBy(_._1)
        .foreach(cr => {

          // Important! What you're looking for is a stable identifier. In Scala, these must either start with an uppercase letter, or be surrounded by backticks.
          val Topic0 = topics(0)

          logger.trace("readKafka1Topic: received message is :" + cr)
          val topic: String = cr._2
          val json = cr._4
          topic match {
            case Topic0 => {
              try {
                val obj: A = net.liftweb.json.parse(json).extract[A]
                logger.trace("readKafka1Topic: identified object of message is :" + obj)
                callbackA(obj)
              } catch {
                case _: Throwable => logger.error("readKafka1Topic: Couldn't convert the received message to an object of %s: %s".format(typeOf[A], cr))
              }
            }
            case _ => logger.error("readKafka1Topic: Couldn't find a suit topic for received message!," + cr)
          }
        })
    })
  }


   */
}
