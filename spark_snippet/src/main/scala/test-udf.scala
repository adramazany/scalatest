package bip.spark.snippet

import org.apache.spark.sql.functions.udf

object TestUDF {
val colors = Map("red" -> "#FF0000",
	"green"->"#00FF00", 
	"blue"->"#0000FF", 
	"black"-> "#FFFFFF", 
	"white"->"#000000", 
	"azure" -> "#F0FFFF", 
	)
val colorUDF = udf((color: String) => {
  val res = colors.find(t => t._1 == color)
  if (res.isDefined) 
	res.get._2
  else None})
}