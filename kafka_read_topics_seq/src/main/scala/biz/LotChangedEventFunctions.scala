package bip.kafka.seqtopics
package biz

import bip.kafka.seqtopics.domain.LotChangedEvent
import bip.kafka.seqtopics.infra.EventHandler

/**
 * @author ramezania
 */
object LotChangedEventFunctions extends EventHandler[LotChangedEvent] {
  override def apply[T](event: T): Unit = {
    println("LotChangedEventFunctions.apply : ",event)
  }
}
