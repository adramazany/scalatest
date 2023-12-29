package bip.kafka.seqtopics
package conf

object sparkConfiguration extends Serializable {

  /**
   * Section for general setting of Spark application.
   */
  val applicationName = scala.util.Properties.envOrElse("APP_NAME", "kafka_read_topics_seq")
  val sparkCheckpointDir = scala.util.Properties.envOrElse("SPARK_CHECKPOINT_DIR", "checkpoints/") + applicationName
  val sparkCassandraConnectorIgnoreNulls = "true"
  val serializer = "org.apache.spark.serializer.KryoSerializer"
  val sparkSqlCaseSensitivity = scala.util.Properties.envOrElse("SPARK_SQL_CASE_SENSITIVITY", false.toString)

  /**
   * General configuration section.
   */
  val streamingInterval = scala.util.Properties.envOrElse("SPARK_STREAMING_INTERVAL", "5").toInt
  val master = scala.util.Properties.envOrElse("SPARK_MASTER", "local[*]")
  val hybridMode = scala.util.Properties.envOrElse("HYBRID_MODE", false.toString).toBoolean

  /**
   * Section for memory settings.
   */
  val driverMemory = scala.util.Properties.envOrElse("SPARK_DRIVER_MEMORY", "1g")
  val executorMemory = scala.util.Properties.envOrElse("SPARK_EXECUTOR_MEMORY", "1g")

  /**
   * Section for CPU settings.
   */
  val executorInstances = scala.util.Properties.envOrElse("SPARK_EXECUTOR_INSTANCES", "2")
  val executorCores = scala.util.Properties.envOrElse("SPARK_EXECUTOR_CORES", "8")

  /**
   * Section for statistical and live calculation of data.
   */
  val accumulationIntervalInMilliseconds = streamingInterval * 1000
  val uphWindow = scala.util.Properties.envOrElse("SPARK_UPH_WINDOW", "5").toInt
  val processNoResultsForStatistics = scala.util.Properties.envOrElse("PROCESS_NO_RESULTS_FOR_STATISTICS", false.toString).toBoolean

  /**
   * Section, which includes cache settings.
   */
  val cacheTimeToLiveForKafkaEvents = scala.util.Properties.envOrElse("CACHE_TTL_FOR_KAFKA_EVENTS", "3600").toLong

  /**
   * Section for partitioning settings.
   */
  val maximumPartitionToCheck = scala.util.Properties.envOrElse("MAXIMUM_PARTITION_TO_CHECK", "2").toInt

  /**
   * Section for rolling production yield settings. Value -1 will be default and with this setting no data will be stored.
   * Settings applies per each stopwatch - station combination.
   */
  val maximumRollingProductionYieldCount = scala.util.Properties.envOrElse("MAXIMUM_ROLLING_PRODUCTION_YIELD_COUNT", "-1").toInt

  /**
   * Section for recent yield calculation. Number of last production results for each stopwatch to take into account for recent yield calculation.
   */
  val maximumRecentProductionResultCount = scala.util.Properties.envOrElse("MAXIMUM_RECENT_PRODUCTION_RESULT_COUNT", "100").toInt

  /**
   * Section for maximum lots to trigger reports. Number of maximum lots we read, that might trigger report generation.
   */
  val maximumLotsToReadForReportGeneration = scala.util.Properties.envOrElse("MAXIMUM_LOTS_TO_READ_FOR_REPORT_GENERATION", "5").toInt

  /**
   * Section for closure of error messages.
   */
  val closeErrorMessagesOnRunningState = scala.util.Properties.envOrElse("CLOSE_ERROR_MESSAGES_ON_RUNNING_STATE", true.toString).toBoolean

  /**
   * Section for detailed measurement report configuration.
   */
  val equipmentValuesInDetailedMeasurementsReport = scala.util.Properties.envOrElse("EQUIPMENT_VALUES_IN_DETAILED_MEASUREMENTS_REPORT", false.toString).toBoolean
  val orderingColumnInDetailedMeasurementsReport = scala.util.Properties.envOrNone("ORDERING_COLUMN_IN_DETAILED_MEASUREMENTS_REPORT")

  /**
   * Section for notification events.
   */
  val yieldNotification = scala.util.Properties.envOrElse("YIELD_NOTIFICATION", false.toString).toBoolean
  val shiftNotification = scala.util.Properties.envOrElse("SHIFT_NOTIFICATION", false.toString).toBoolean
  val productionPlanNotification = scala.util.Properties.envOrElse("PRODUCTION_PLAN_NOTIFICATION", false.toString).toBoolean

  /**
   * Section for monitoring reset type.
   */
  val monitoringResetType = scala.util.Properties.envOrElse("MONITORING_RESET_TYPE", "BOTH")

  /**
   * Section for parallel search.
   * Settings will determine how many of records will be scanned for parallely open items, not how many of records is really open.
   */
  val parallelParentItemsToReadCount = scala.util.Properties.envOrElse("PARALLEL_PARENT_ITEMS_TO_READ_COUNT", "10").toInt
  val parallelErrorsToReadCount = scala.util.Properties.envOrElse("PARALLEL_ERRORS_TO_READ_COUNT", "40").toInt
}