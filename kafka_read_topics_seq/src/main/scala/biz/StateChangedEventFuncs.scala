package bip.kafka.seqtopics
package biz

/**
 * @author ramezania
 */
object StateChangedEventFuncs {
  def apply[T](event: T): Unit = {
    println("StateChangedEventFuncs.apply : ", event)
    Thread.sleep(1000)
  }
}
