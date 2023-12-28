package bip.kafka.seqtopics
package biz

import bip.kafka.seqtopics.infra.EventHandler
import bip.kafka.seqtopics.domain.LotChangedEvent

/**
 * @author ramezania
 */
object D1Functions  extends EventHandler[LotChangedEvent]{
  override def apply[T](event: T): Unit = {
    println("D1Functions.apply : ", event)
  }
}
