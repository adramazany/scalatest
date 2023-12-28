package bip.kafka.seqtopics
package conf

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

object kafkaConfiguration extends Serializable {
  val kafkaHost = scala.util.Properties.envOrElse("KAFKA_HOST", "localhost:9092")
  val kafkaMaxMessageSize = scala.util.Properties.envOrElse("KAFKA_MAX_MESSAGE_SIZE", "52428800")
  val kafkaConsumerCacheCapacity = scala.util.Properties.envOrElse("KAFKA_CONSUMER_CACHE_CAPACITY", "100")
  val kafkaStringSerializer = classOf[StringSerializer]
  val kafkaStringDeserializer = classOf[StringDeserializer]

  val kafkaParams = Map[String, Object](
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> kafkaHost,
    ConsumerConfig.FETCH_MAX_BYTES_CONFIG -> kafkaMaxMessageSize,
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> kafkaStringDeserializer,
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> kafkaStringDeserializer,
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> (false: java.lang.Boolean))

  val kafkaProducerConfiguration = new java.util.HashMap[String, Object]()
  kafkaProducerConfiguration.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost)
  kafkaProducerConfiguration.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaStringSerializer)
  kafkaProducerConfiguration.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaStringSerializer)
}