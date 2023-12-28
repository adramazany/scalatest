package bip.spark.snippet

:load test-df.scala
:load test-complex-udf.scala

dff = dff.withColumn("oc",concat(col("oc"),lit("-A")))
dff = dff.alias("dff")
// var dfj = df.join(dff, df("timestamp")===dff("timestamp"), "left")
var dfj = df.alias("df").join(dff, col("df.timestamp")===col("dff.timestamp"), "left")
dfj.show
dfj.select("dff.oc").show

