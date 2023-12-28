package bip.spark.snippet

:load complex-udf.scala
:load test-df.scala

val openClose = Map(
	"O" -> "Open", 
	"C"->"Close")
	
var dff = df.withColumn("FullName",lookupUDF(openClose)(col("oc")))
dff.show
