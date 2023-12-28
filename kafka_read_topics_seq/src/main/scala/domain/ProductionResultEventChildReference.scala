package bip.kafka.seqtopics
package domain

import net.liftweb.json.JsonAST.JValue

/**
 * @author ramezania
 */
case class ProductionResultEventChildReference(
  name:       String,
  child:      Option[Array[ProductionResultEventChildReference]],
  result:     Option[String],
  dataType:   Option[String],
  value:      Option[JValue],
  lowerLimit: Option[Float],
  upperLimit: Option[Float],
  unit:       Option[String],
  paramList:  Option[Map[String, String]])
