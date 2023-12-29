package bip.kafka.seqtopics
package biz

/**
 * @author ramezania
 */
object ProductionResultEventFuncs {
  def apply[T](event: T): Unit = {
    println("ProductionResultEventFuncs.apply : ", event)
    Thread.sleep(3000)
  }
}
