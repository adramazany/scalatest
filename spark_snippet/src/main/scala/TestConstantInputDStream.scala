import java.util.Arrays
import java.util.stream.Collectors
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * @author ramezania
 */
object TestConstantInputDStream {

  def main1(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "Constant Input DStream Demo", new SparkConf())
    import org.apache.spark.streaming.{ StreamingContext, Seconds }
    val ssc = new StreamingContext(sc, batchDuration = Seconds(5))

    // Create the RDD
    val rdd = sc.parallelize(0 to 9)

// Create constant input dstream with the RDD
    import org.apache.spark.streaming.dstream.ConstantInputDStream
    val cis = new ConstantInputDStream(ssc, rdd)

// Sample stream computation
    println("ConstantInputDStream 0..9")
    cis.map(i => { println(i)
      return i.toString }).print()
    cis.foreachRDD(rdd => rdd.toString() )
    cis.print
}

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "Constant Input DStream Demo", new SparkConf())

/*
    val sparkConf2 = new Nothing().setAppName("NetworkWordCount")
    val ssc2 = new Nothing(sparkConf2, new Nothing(10000))
    val data = Arrays.asList(1, 2, 3, 4, 5)
    val distData = ssc2.sparkContext.parallelize(data)
    val numStream = new Nothing(new Nothing(ssc2, distData))

    try {
      val streamCtxt = new Nothing(sc, new Nothing(1000))
      try {
        val data = Arrays.asList(1, 2, 3, 4, 5)
        val distData = streamCtxt.sparkContext.parallelize(data)
        val evidence = ClassTag$.MODULE$.apply(classOf[Integer])
        val integerConstantInputDStream = new Nothing(streamCtxt.ssc, distData.rdd, evidence)
        val list = new Nothing
        val javaDStream = JavaDStream.fromDStream(integerConstantInputDStream, evidence)
        javaDStream.foreachRDD((r) => list.addAll(r.collect))
        streamCtxt.start
        streamCtxt.awaitTerminationOrTimeout(2000)
        streamCtxt.stop
        log.info("here is the list: " + list.stream.map((j) => String.valueOf(j)).collect(Collectors.joining(",")))
      } finally if (streamCtxt != null) streamCtxt.close()
    }
*/
  }
}
