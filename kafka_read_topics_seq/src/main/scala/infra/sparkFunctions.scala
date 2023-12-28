package bip.kafka.seqtopics
package infra

import bip.kafka.seqtopics.conf.kafkaConfiguration
import bip.kafka.seqtopics.conf.sparkConfiguration
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._

/**
 * @author ramezania
 */
object sparkFunctions {
  val applicationName = scala.util.Properties.envOrElse("APP_NAME", "kafka_read_topics_seq")

  val sparkConf = new SparkConf()
    .setMaster(sparkConfiguration.master)
    .setAppName(sparkConfiguration.applicationName)
//    .set("spark.cassandra.connection.host", cassandraConfiguration.host)
//    .set("spark.cassandra.connection.port", cassandraConfiguration.port)
//    .set("spark.cassandra.connection.ssl.enabled", cassandraConfiguration.sslEnabled)
//    .set("spark.cassandra.connection.ssl.trustStore.path", cassandraConfiguration.trustStorePath)
//    .set("spark.cassandra.connection.ssl.trustStore.password", cassandraConfiguration.trustStorePassword)
//    .set("spark.cassandra.connection.keepAliveMS", cassandraConfiguration.keepAliveInMs)
//    .set("spark.cassandra.auth.username", cassandraConfiguration.username)
//    .set("spark.cassandra.auth.password", cassandraConfiguration.password)
    .set("spark.cassandra.output.ignoreNulls", sparkConfiguration.sparkCassandraConnectorIgnoreNulls)
    .set("spark.serializer", sparkConfiguration.serializer)
    .set("spark.streaming.kafka.consumer.cache.maxCapacity", kafkaConfiguration.kafkaConsumerCacheCapacity)
    .set("spark.sql.caseSensitive", sparkConfiguration.sparkSqlCaseSensitivity)
//    .set("spark.sql.thriftServer.queryTimeout", cassandraConfiguration.thriftServerQueryTimeout)

//  val ssc = new StreamingContext(sparkConf, Seconds(sparkConfiguration.streamingInterval))
//  ssc.checkpoint(sparkConfiguration.sparkCheckpointDir)

  private var fromCheckpoint = true

  // Set number of partitions for shuffling = (number of executors * number of cores per executor)
  val numExecutors = sparkConf.get("spark.executor.instances", sparkConfiguration.executorInstances).toInt
  val numExecutorsCores = sparkConf.get("spark.executor.cores", sparkConfiguration.executorCores).toInt
  sparkConf.set("spark.sql.shuffle.partitions", (numExecutors * numExecutorsCores).toString)


  val spark = SparkSession.builder().config(sparkConf).getOrCreate()

  val ssc: StreamingContext  = new StreamingContext(sparkConf, Seconds(sparkConfiguration.streamingInterval))
  ssc.checkpoint(sparkConfiguration.sparkCheckpointDir)

/*
  private def runWorkers(machineIdsBroadcast: BroadcastWrapper[List[String]]) {
    if (!fromCheckpoint) {
      if (!Configuration.sparkConfiguration.hybridMode && workerList.hasBatchWorkers) {
        logInfo("Registering handler for data processing requests.")
        val batchWorkers = workerList.getBatchWorkerListWithJobNames
        DataProcessingRequestCollectionJob.processDataProcessingRequests(ssc, Configuration.sparkConfiguration.applicationName, machineIdsBroadcast, batchWorkers)
        logInfo(s"Data processing request handler will be processing following requests: ${batchWorkers.map(_._1).mkString(",")}")
      }
      logDebug("Executing workers successively.")
      for (worker <- workerList.getWorkerList()) {
        if (isWorkerActive(worker._2)) {
          logInfo(s"Executing worker: ${worker._2.getClass.getCanonicalName.extractClassName}")
          worker._2.doWork()
        }
      }
    }
  }
*/

}
