package intervalTree

/**
 * @author ramezania
 */
object Test {
  def main(args: Array[String]): Unit = {
    val intervals = List(
      new Interval(1, 5, 'i'),
      new Interval(6, 9, 'g'),
      new Interval(10, 14, 'b')
    )
    val tree = IntervalTree(intervals)

    tree.getIntervals(0,20).foreach(f => print(f))


  }
}
