package bip.kafka.seqtopics
package biz

/**
 * @author ramezania
 */
object OperatorLogEventFuncs {
  def apply[T](event: T): Unit = {
    println("OperatorLogEventFuncs.apply : ", event)
  }
}
