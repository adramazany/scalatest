package bip.spark.snippet

import org.apache.spark.sql.functions.udf

object ComplexUDF{
def lookupUDF(map: Map[String, String]) = udf( (key: String)=>{
  val res = map.find(t => t._1 == key)
  if (res.isDefined)
	res.get._2 
  else ""})
}