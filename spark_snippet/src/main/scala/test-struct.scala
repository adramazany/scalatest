package bip.spark.snippet

:load test-df.scala

var sdf = df.withColumn("position", struct( lit(1).as("x"), lit(2).as("y"), lit(3).as("z")))
sdf = sdf.withColumn("s1", struct( lit(null).cast("Integer").as("x"), lit(null).cast("Double").as("y"), lit(null).cast("Long").as("z")))

sdf.printSchema

sdf = sdf.na.fill(-1, Seq("s1.x", "s1.y"))
print("sdf.na.fill(-1... not work properly! ")
	
sdf.show
