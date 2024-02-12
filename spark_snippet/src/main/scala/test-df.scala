package bip.spark.snippet

import org.apache.spark.sql.SparkSession

object TestDf {
  lazy val spark = SparkSession.builder().getOrCreate()

  import spark.implicits._

  val columns = Seq("timestamp", "oc")
  val data = Seq((1, "C"), (5, "O"), (3, "C"), (4, "C"), (2, "O"), (6, "O"), (8, "C"), (7, "O"))
  val rdd = spark.sparkContext.parallelize(data)
  val df = rdd.toDF(columns: _*)
  df.printSchema()
  df.show()
}