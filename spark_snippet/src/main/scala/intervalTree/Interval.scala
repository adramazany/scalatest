package intervalTree

/**
 * @author ramezania
 *
 * Interval represents a time interval along some associated data.
 */

case class Interval[T](start: Long,end: Long,data: T) extends Comparable[Interval[T]] {

  /**
   * return the length of the interval
   */
  def length: Long = end-start

  /**
   * Checks if the interval contains the given point.
   */
  def contains(p: Long): Boolean = start <= p && p<= end

  /**
   * Checks if the interval contains another interval.
   */
    def contains(i: Interval[T]): Boolean = start <= i.start && i.end <= end

  /**
   * Checks if the interval intersects (or touches) another interval.
   */
  def intersects(i: Interval[T]): Boolean = start <= i.end && end >= i.start

  /**
   * Checks if the given interval intersects (or touches) the given range.
   */
  def intersects(from: Long, to: Long): Boolean = start <= to && end >= from

  /**
   * Computes the distance to another interval.
   */
    def distance(i: Interval[T]): Long = {
      if(intersects(i)) 0
      else if(start < i.start) i.start - end
      else start - i.end
    }

  /**
   * Compares the interval to another interval.
   */
  override def compareTo(i: Interval[T]): Int = {
    if(start < i.start) -1
    else if(start > i.start) 1
    else if(end < i.end) -1
    else if(end > i.end) 1
    else 0
  }

  /**
   * Checking equality.
   */
  override def equals(obj: Any): Boolean = {
    if(obj.isInstanceOf[Interval[T]]){
      val i = obj.asInstanceOf[Interval[T]]
      i.start == start && i.end == end && i.data == data
    }else false
  }

}
