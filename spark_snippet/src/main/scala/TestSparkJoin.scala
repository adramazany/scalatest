import org.apache.spark.sql.catalyst.dsl.expressions.StringToAttributeConversionHelper
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.functions.when

/**
 * @author ramezania
 */
object TestSparkJoin {
  def main(args: Array[String]): Unit = {
    println("TestSparkJoin starting ...")

    val detailed = MySparkSession.spark().read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "detailed_production_measurement_stats", "keyspace" -> "detailed_production_measurement_stats_stream"))
      .load()
    detailed.show(false)

    var lot = MySparkSession.spark().read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "lot", "keyspace" -> "detailed_production_measurement_stats_stream"))
      .load()
    lot.show(false)

//    lot.filter("end_timestamp==-1")["end_timestamp"]=Long.MaxValue
    import org.apache.spark.sql.functions
//    lot.filter("end_timestamp==-1").withColumn("end_timestamp", lit(Long.MaxValue))
//    lot = lot.withColumn("end_timestamp", when($"end_timestamp" === -1, $"end_timestamp" ))
    lot = lot.withColumn("end_timestamp", when(col("end_timestamp") === -1 , lit(Long.MaxValue) ).otherwise( col("end_timestamp") ) )
    lot.show()

    val detailed_lot = detailed.join(lot
      ,(detailed.col("machine_id") === lot.col("machine_id")
        && detailed.col("timestamp") >= lot.col("start_timestamp")
        && detailed.col("timestamp") <= lot.col("end_timestamp") ))

    detailed_lot.show(false)

    println("TestSparkJoin finished.")
  }
}
