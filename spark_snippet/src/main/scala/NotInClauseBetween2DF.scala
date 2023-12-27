import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrameNaFunctions

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._
import com.datastax.spark.connector.toRDDFunctions
import org.apache.spark.sql.Dataset
import com.datastax.spark.connector.toNamedColumnRef
import com.datastax.spark.connector.SomeColumns

import org.apache.spark.sql.Encoders

/**
 * @author ramezania
 */
object NotInClauseBetween2DF {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("MySparkSession")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.sqlContext.implicits._


    val machines = List(
      Machine("M1", 1),
      Machine("M1", 3),
      Machine("M1", 2, None),
      Machine("M1", 2, Some("Lot-1")),
      Machine("M1", 2, Some("Lot-2")))
//      Machine("M2", 2, Some("Lot-3")))
    val dfM = machines.toDF
    dfM.printSchema()
    dfM.show(false)

    val lots = List(
      /*Lot3(None),*/
      Lot3(Some("Lot-1"), Some("Lot-1"), Some("1")),
      Lot3(Some("Lot-3"), Some("Lot-3"), Some("3")),
      Lot3(Some("Lot-5"), Some("Lot-5"), Some("5")))
//      Machine("M2", 2, Some("Lot-3")))
    val dfL = lots.toDF
    dfL.printSchema()
    dfL.show(false)

    val joinExpr = dfL("lotId") === dfM("lotId")
//    dfL.join(dfM, joinExpr, "left_anti" )
//    dfL.na.fill("-",Seq("lotId")).join(dfM.na.fill("-",Seq("lotId")), dfL("lotId") === dfM("lotId"), "left_anti" )
    val dfLjoinM = dfL.join(dfM, dfL("lotId") === dfM("lotId"), "left_anti" )
    dfLjoinM.show

    println( dfLjoinM.as[Lot3].collect().mkString("\n"))

  }
}

case class Machine(machineId: String, stopwatchId: Int, lotId: Option[String]= None )
case class Lot3(lotId: Option[String], lotName: Option[String], lotSize: Option[String])