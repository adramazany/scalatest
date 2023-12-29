package bip.kafka.seqtopics
package biz

/**
 * @author ramezania
 */
object RecipeChangedEventFuncs {
  def apply[T](event: T): Unit = {
    println("RecipeChangedEventFuncs.apply : ", event)
  }
}
