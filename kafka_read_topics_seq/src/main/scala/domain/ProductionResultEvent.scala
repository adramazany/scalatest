package bip.kafka.seqtopics
package domain

/**
 * @author ramezania
 */
case class ProductionResultEvent(
  machineId:    String,
  station:      String,
  lotId:        Option[String],
  results:      Option[Array[ProductionResultEventChildReference]],
  itemId:       Option[String],
  paramList:    Option[Map[String, String]],
  globalResult: String,
  isGlobal:     Boolean,
  isStation:    Boolean,
  isDetailed:   Option[Boolean],
  isTraceable:  Option[Boolean],
  timestamp:    Long)
