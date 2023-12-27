
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @author ramezania
 */
object TestRDD_keyBy_reduceByKey {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("MySparkSession")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.sqlContext.implicits._

    val data = List(
      MachineLotProducedUnitData2(19,"L1",1,"M1",2),
      MachineLotProducedUnitData2(21,"L1",1,"M1",5),
      MachineLotProducedUnitData2(21,"L2",1,"M1",1),
      MachineLotProducedUnitData2(21,"L2",1,"M1",3),
      MachineLotProducedUnitData2(25,"L1",1,"M2",3)
    )
    val lotProducedUnitDataRDD = data.toDS().rdd

    lotProducedUnitDataRDD.keyBy(prod => (prod.machineId)).reduceByKey((x, _) => x)
      .toDS().show()

    lotProducedUnitDataRDD.keyBy(prod => (prod.machineId)).reduceByKey((x, _) => x).values
      .toDS().show()

    lotProducedUnitDataRDD.keyBy(prod => (prod.machineId)).reduceByKey((x, _) => x).values
      .map(resultData => (resultData.machineId, resultData.lotProducedUnitData))
      .toDS().show(false)

    lotProducedUnitDataRDD.keyBy(prod => (prod.machineId)).reduceByKey((x, _) => x).values
      .map(resultData => ((resultData.machineId,resultData.lotId), resultData.lotProducedUnitData))
      .toDS().show(false)

    lotProducedUnitDataRDD.keyBy(prod => (prod.machineId,prod.lotId))
      .reduceByKey((x,y)=>{
        x.lotProducedUnitData+=y.lotProducedUnitData
        x })
      .toDS().show(false)

  }

}

case class MachineLotProducedUnitData2(
  lotProgress:         Int,
  lotId:               String,
  metaDataId:          Int,
  machineId:           String,
  var lotProducedUnitData: Int)
