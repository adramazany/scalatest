/**
 * @author ramezania
 */
object SumWithListOfInt {
  def main(args: Array[String]): Unit = {
    var x = 10
    val l = List(5,3,1)
    x += l.sum
    println(x)

    val l2 = List(M("a",2), M("B",4), M("v",1), M("d",5))
    x += l2.sortBy(f => f.y).map(f => f.y).sum
    println(x)

    val l3 = Option(List(M2(Some("a"),Some(2)), M2(Some("B"),None), M2(None,Some(1)), M2(Some("d"),None)))
    println(l3.get.map(_.y.get).sum)

  }
}

case class M (n: String, y: Int)
case class M2 (n: Option[String], y: Option[Int])
