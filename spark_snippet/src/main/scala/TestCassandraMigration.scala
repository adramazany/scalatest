import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkContext
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.SparkSession

/**
 * @author ramezania
 */

object TestCassandraMigration {

  def main(args: Array[String]): Unit = {
    println("TestCassandraMigration starting ...")
    val applicationName = "TestCassandraMigration"
    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(applicationName)
      .set("spark.cassandra.connection.host", "localhost")
      .set("spark.cassandra.auth.username", "admin")
      .set("spark.cassandra.auth.password", "admin")
      .set("spark.cassandra.connection.keep_alive_ms", "60000")
      .set("spark.cassandra.output.ignoreNulls", "true")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.streaming.kafka.consumer.cache.maxCapacity", "100")
      .set("spark.sql.caseSensitive",  false.toString)
    val cassandraConnector = CassandraConnector(sparkConf)
    val numExecutors = sparkConf.get("spark.executor.instances", "2").toInt
    val numExecutorsCores = sparkConf.get("spark.executor.cores", "8").toInt
    sparkConf.set("spark.sql.shuffle.partitions", (numExecutors * numExecutorsCores).toString)

    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

//    val partitionsInfo = ssc.cassandraTable[V1_7_0__ParentItem]("traceable_production_measurement", "parent_item")
//    spark.read
//      .format("org.apache.spark.sql.cassandra")
//      .options(Map("table" -> "books", "keyspace" -> "books_ks"))
//      .load.createOrReplaceTempView("books_vw")

    val rdd = spark.read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "parent_item", "keyspace" -> "traceable_production_measurement_stream"))
      .load()

//    println(StringUtils.toString(rdd))
    rdd.toDF().show(false)
//    println(rdd.collect().mkString(","))

//    rdd.createOrReplaceTempView("traceable_production_measurement_stream_parent_item_temp") // session scope
    rdd.createOrReplaceGlobalTempView("traceable_production_measurement_stream_parent_item_temp") // keep alive until the Spark application terminates , otherwise use table

//    val rdd2 = spark.newSession().sql("select * from global_temp.traceable_production_measurement_stream_parent_item_temp")
//    or
    if (spark.catalog.tableExists("global_temp.traceable_production_measurement_stream_parent_item_temp")) {
      val df = spark.newSession().table("global_temp.traceable_production_measurement_stream_parent_item_temp")
      df.show(false)
      import spark.implicits._ /*resolve encoder exception in the next line*/
      val rdd2 = df.map(row => V1_7_0__ParentItem(
        row.getAs("machine_id"),
        row.getAs[Long]("partition_start_timestamp"),
        row.getAs[Long]("start_timestamp"),
        row.getAs("product_type"),
        row.getAs("item_id"),
        /*row.getAs("physical_item_id"),*/
        row.getAs[Seq[String]]("physical_item_id").mkString, /*Seq just worked and List/Array not worked and throwed the exception : scala.collection.mutable.WrappedArray$ofRef cannot be cast to [Ljava.lang.Object;*/
        row.getAs("item_sub_type")
      ))
//      println(rdd2.collect().mkString(","))
      rdd2.toDF().show(false)
//      rdd2.rdd.saveAsTextFile()

    }else{
      println("global_temp.traceable_production_measurement_stream_parent_item_temp table not found!")
    }

    println("TestCassandraMigration done.")
  }
}

case class V1_7_0__ParentItem(
  machineId:               String,
  partitionStartTimestamp: Long,
  startTimestamp:          Long,
  productType:             String,
  itemId:                  String,
  physicalItemId:          String,
  itemSubType:             String)
