package bip.spark.snippet

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

//:load test-df.scala
//:load test-complex-udf.scala

object TestJoin {
  def testJoin(df: DataFrame, dff: DataFrame): Unit= {
  var dff = df.withColumn("oc", concat(col("oc"), lit("-A")))
  dff = dff.alias("dff")
  // var dfj = df.join(dff, df("timestamp")===dff("timestamp"), "left")
  var dfj = df.alias("df").join(dff, col("df.timestamp") === col("dff.timestamp"), "left")
  dfj.show
  dfj.select("dff.oc").show
}
}