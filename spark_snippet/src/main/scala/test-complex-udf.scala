package bip.spark.snippet

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.Column
import org.apache.spark.sql.DataFrame

//:load complex-udf.scala
//:load test-df.scala
object TestComplexUDF {
	def test_complex_udf(df: DataFrame, lookupUDF: (Map[String, String]) => Column) {
		val openClose = Map(
			"O" -> "Open",
			"C" -> "Close")

		var dff = df.withColumn("FullName", lookupUDF(openClose)(col("oc")))
		dff.show
	}
}