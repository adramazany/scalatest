package bip.kafka.seqtopics
package infra

/**
 * @author ramezania
 */
trait EventHandler[T] {
  def apply[T](event: T)
}
