package bip.kafka.seqtopics
package domain

/**
 * @author ramezania
 */
case class StateChangedEvent(
  machineId:          String,
  state:              String,
  timestamp:          Option[Long],
  connectionLostType: Option[String])
