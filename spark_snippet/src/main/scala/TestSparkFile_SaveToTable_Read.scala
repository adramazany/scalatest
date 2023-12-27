import org.apache.spark.sql.SaveMode
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import MySparkSession.sparkConf
import java.io.File
import scala.tools.nsc.io.Directory


/**
 * @author ramezania
 */
object TestSparkFile_SaveToTable {
  def main(args: Array[String]): Unit = {
    val sparkSession = MySparkSession.spark()
    import sparkSession.implicits._
    sparkSession.read
      .format("org.apache.spark.sql.cassandra")
      .option("keyspace", "traceable_production_amount_stream")
      .option("table", "lot")
      .load()
      .write
      .mode(SaveMode.Overwrite)
      .save("./spark-warehouse/lot_temp")
  }
}

object TestSparkFile_ReadTable {
  def main(args: Array[String]): Unit = {
    val sparkSession = MySparkSession.spark()
    val df = sparkSession
      .read.load("./spark-warehouse/lot_temp")
      df.show
    Directory("./spark-warehouse/lot_temp").deleteRecursively()

  }

}
