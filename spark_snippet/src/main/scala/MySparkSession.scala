import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @author ramezania
 */
object MySparkSession {

  var _sparkConf:SparkConf=null
  def sparkConf(): SparkConf = {
    if(_sparkConf==null) {
      val applicationName = "MySparkSession"
      _sparkConf = new SparkConf()
        .setMaster("local[*]")
        .setAppName(applicationName)
        .set("spark.cassandra.connection.host", "localhost")
        .set("spark.cassandra.auth.username", "admin")
        .set("spark.cassandra.auth.password", "admin")
        .set("spark.cassandra.connection.keep_alive_ms", "60000")
        .set("spark.cassandra.output.ignoreNulls", "true")
        .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        .set("spark.streaming.kafka.consumer.cache.maxCapacity", "100")
        .set("spark.sql.caseSensitive", false.toString)
      val numExecutors = _sparkConf.get("spark.executor.instances", "2").toInt
      val numExecutorsCores = _sparkConf.get("spark.executor.cores", "8").toInt
      _sparkConf.set("spark.sql.shuffle.partitions", (numExecutors * numExecutorsCores).toString)
    }
    _sparkConf
  }

  def spark(): SparkSession = {
    SparkSession.builder().config(sparkConf).getOrCreate()
  }

}
