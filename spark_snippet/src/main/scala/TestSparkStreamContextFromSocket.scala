import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext

object TestSparkStreamContextFromSocket {
  def main(args: Array[String]): Unit = {
    println("test !!!")
    val sparkConf = new SparkConf().setAppName("TestSparkApp").setMaster("local[*]")
//    val sc = new SparkContext(sparkConf)
//    val ssc = new StreamingContext(sparkConf, Seconds(Configuration.sparkConfiguration.streamingInterval))
    val ssc = new StreamingContext(sparkConf,Seconds(10))
//    val lines = ssc.socketTextStream("localhost",9999) // nc -lk 9999    >   hello world
//    val lines = ssc.textFileStream("test-sparkstream-input.txt")
    val lines = ssc.textFileStream("C:\\Development\\workspace\\test\\src\\main\\resources\\")
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    lines.print()
    words.print()
    pairs.print()
    wordCounts.print()

    println("------------------")
    print(words)

    ssc.start()
    ssc.awaitTermination()
//    ssc.stop()
  }
}
