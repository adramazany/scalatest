package bip.kafka.seqtopics
package domain

/**
 * @author ramezania
 */
case class LotChangedEvent(
  machineId:  String,
  changeType: String,
  name:       String,
  id:         String,
  `type`:     Option[String],
  size:       Option[Long],
  unit:       Option[String],
  timestamp:  Long,
  parameters: Option[Map[String, String]])
