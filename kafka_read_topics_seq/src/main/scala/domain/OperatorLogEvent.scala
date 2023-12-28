package bip.kafka.seqtopics
package domain

/**
 * @author ramezania
 */
case class OperatorLogEvent(
  machineId: String,
  logType:   String,
  name:      String,
  timestamp: Long)
