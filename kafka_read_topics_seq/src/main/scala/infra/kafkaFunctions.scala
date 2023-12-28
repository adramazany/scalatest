package bip.kafka.seqtopics
package infra

/**
 * @author ramezania
 */
object kafkaFunctions {

//  var stream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent, Subscribe[String, String](topics, kafkaParams))

  def readKafka(topics:Array[String]): Unit = {
    print(topics.toList)
  }
}
