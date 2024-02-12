package bip.spark.snippet

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import scala.collection.mutable.ListBuffer

object TestDfFindRanges {
	lazy val spark = SparkSession.builder().getOrCreate()

val columns = Seq("timestamp","oc")
val data = Seq((1, "C"), (5, "O"), (3, "C"), (4, "C"), (2, "O"), (6, "O"), (8, "C"), (7, "O"))
import spark.implicits._
val rdd = spark.sparkContext.parallelize(data)
val df = rdd.toDF(columns:_*)

val df_o = df.where("oc='O'").sort("timestamp")
df_o.show()
val df_c = df.where("oc='C'").sort("timestamp")
df_c.show()

val used = ListBuffer[Int]()
var res = List[(Int,Int)]()
for(o <- df_o.rdd.collect){
	val o_t = o.getAs[Int]("timestamp")
	val found_c = df_c.filter(!col("timestamp").isin(used.toSeq:_*) && col("timestamp")>o_t)
	if(found_c.count()>0){
		val c = found_c.first()
		val c_t = c.getAs[Int]("timestamp")
		used += c_t
		res ::= (o_t, c_t)
	}
}

print(res.toSeq)

}