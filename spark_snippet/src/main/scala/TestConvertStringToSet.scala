/**
 * @author ramezania
 */
object TestConvertStringToSet {

  def cnvSet(str: String,sep: String): Set[String] = {
    val s=str.split(sep).map(_.trim).toSet
    println(s"cnvSet(${str})=${s.size}, ${s}")
    s
  }

  def main(args: Array[String]): Unit = {
    println(Set("s1","s2"))
    cnvSet("s1",",")
    cnvSet("s1,s2",",")
    cnvSet("[s1  ,s2]",",")
    cnvSet("(s1,  s2)",",")

/*
    val kafkaParams = Map(
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "spark-streaming-notes",
      "auto.offset.reset" -> "earliest")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferBrokers,
      ConsumerStrategies.Subscribe[String, String](Set("topic I", "topic I"), kafkaParams))
*/

}
}
