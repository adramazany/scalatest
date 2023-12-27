import org.apache.spark.sql.SaveMode
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import MySparkSession.sparkConf

/**
 * @author ramezania
 */
object TestSparkHive_SaveToTable {
  def main(args: Array[String]): Unit = {
//    val sparkSession = MySparkSession.spark()
    val sparkSession = SparkSession.builder().config(sparkConf)
      .enableHiveSupport()
      .getOrCreate()
    import sparkSession.implicits._
    sparkSession.sql("CREATE DATABASE IF NOT EXISTS test")
    sparkSession.read
      .format("org.apache.spark.sql.cassandra")
      .option("keyspace", "traceable_production_amount_stream")
      .option("table", "lot")
      .load()
      .write
      .mode(SaveMode.Overwrite)
      .saveAsTable("test.lot_temp")
  }
}

object TestSparkHive_ReadTable {
  def main(args: Array[String]): Unit = {
//    val sparkSession = MySparkSession.spark()
  val sparkSession = SparkSession.builder().config(sparkConf)
    .enableHiveSupport()
    .getOrCreate()
    val df = sparkSession
      .table("test.lot_temp")
      df.show
    sparkSession.sql("drop table test.lot_temp")
  }

}
