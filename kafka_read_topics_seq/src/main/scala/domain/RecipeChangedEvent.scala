package bip.kafka.seqtopics
package domain

/**
 * @author ramezania
 */
case class RecipeChangedEvent(
  machineId:      String,
  recipeType:     String,
  name:           String,
  idealCycleTime: Integer,
  parameters:     Option[Map[String, String]],
  timestamp:      Long)
