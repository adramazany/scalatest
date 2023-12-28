package bip.spark.snippet

def lookupUDF(map: Map[String, String]) = udf( (key: String)=>{
  val res = map.find(t => t._1 == key)
  if (res.isDefined)
	res.get._2 
  else ""})
