import org.apache.spark.streaming.dstream.DStream

/**
 * @author ramezania
 */
object TestSparkDStreamUnitTest {

  def main(args: Array[String]): Unit = {

  }

  def count(lines: DStream[String]): DStream[(String, Int)] =
    lines.flatMap(_.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)


}
