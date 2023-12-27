import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author ramezania
 */
class TestHowRDDFlatmapWorks {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("MySparkSession")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.sqlContext.implicits._

    val data = List(
      GlobalProductionResultRawData("M1", 1, List(ProductionResultEvent("M1","S1","L1"),ProductionResultEvent("M1","S1","L2") )),
      GlobalProductionResultRawData("M1", 1, List(ProductionResultEvent("M1","S1","L1") )),
      GlobalProductionResultRawData("M2", 1, List(ProductionResultEvent("M2","S1","L1"),ProductionResultEvent("M2","S1","L2") ))
    )

    val rdd: RDD[GlobalProductionResultRawData] = data.toDS.rdd
    val lotProducedUnitDataRDD = rdd.mapPartitions(productionResultRawData => {
      productionResultRawData.filter(_.metaDataId == 1).toList
        .flatMap(productionResultRaw => {
          val allProducedUnits = productionResultRaw.productionResultEvents.filter(_.station == "S1").size
          val lotProgress = Math.round(allProducedUnits.toFloat / 500)
          val machineId = productionResultRaw.productionResultEvents.apply(0).machineId
          Array(MachineLotProducedUnitData(lotProgress,1,machineId,allProducedUnits))
        }).toIterator
    })
  }
}

case class GlobalProductionResultRawData(
  val machineId:              String,
  val metaDataId:             Int,
  val productionResultEvents: List[ProductionResultEvent]
)

case class ProductionResultEvent(
  machineId:    String,
  station:      String,
  lotId:        String)

case class MachineLotProducedUnitData(
  lotProgress:         Int,
  metaDataId:          Int,
  machineId:           String,
  lotProducedUnitData: Int)