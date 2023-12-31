package bip.spark.snippet

import spark.implicits._
import scala.collection.mutable.ListBuffer

val columns = Seq("timestamp","oc")
val data = Seq((1, "C"), (5, "O"), (3, "C"), (4, "C"), (2, "O"), (6, "O"), (8, "C"), (7, "O"))
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


val columns2 = Seq("timestamp","name")
val data2 = Seq((1, "AAA"), (5, "EEE"), (3, "CCC"), (4, "DDD"), (2, "BBB"), (6, "FFF"), (8, "HHH"), (7, "GGG"))
val rdd2 = spark.sparkContext.parallelize(data)
val df2 = rdd.toDF(columns:_*)

//var sb = res.map(oc => { s"timestamp between ${oc._1} AND ${oc._2}" }).mkString(" OR ")
var sb = res.map(oc => { s"timestamp between ${oc._1} AND ${oc._2}" }).reduce(_+" OR "+_)

df2.where(sb).show()
df2.where("NOT("+sb+")").show()

