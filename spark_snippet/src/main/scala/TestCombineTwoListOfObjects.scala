import scala.reflect.internal.util.Collections

/**
 * @author ramezania
 */
object TestCombineTwoListOfObjects {

  def main(args: Array[String]): Unit = {
    val l1 = List(1,2)
    val l2 = List(2,3)
    println((l1 ++ l2))

    val s1 = Set(1,2)
    val s2 = Set(2,3)
    println((s1 ++ s2))

    val obj1 = test(1, "one")
    val obj2 = test(2, "two")
    val list1 = List(obj1, obj2)

    val obj3 = test(2, "Another two")
    val obj4 = test(4, "four")
    val list2 = List(obj3, obj4)

    println((list1 ++ list2))
    println((list1 ::: list2))
    println((List.concat(list1,list2) ))


    val set1 = Set(obj1, obj2)
    val set2 = Set(obj3, obj4)
    println((set2 ++ set1))

    println((list2.toSet ++ list1.toSet).toList)
    println((list2 ++ list1).toSet.toList)
//    println((list2 ++ list1).distinct) // not merged correctly
//    println((list2 ++ list1).distinctBy(_.i)) // after the version of Scala 2.13

    println((Some(list2).get ++ Some(list1).get).toSet.toList)

  }

}

case class test(i: Int, str: String) {
  override def equals(obj: Any): Boolean = this.i == obj.asInstanceOf[test].i
//  override def canEqual(that: Any): Boolean = this.i == that.asInstanceOf[test].i
}


