/**
 * @author ramezania
 */
object TestObjectIsListOrNot {
  def main(args: Array[String]): Unit = {
    val l = List("1", "2", "3")
    val s = "[1,2]"
    print( makeList( l ) )
    print( makeList( s ) )
    print( List(s.replaceAll("\\[|\\]|,"," ")) )

  }
  def makeList(o: Any): List[_] = {
    if(o.isInstanceOf[List[Any]])o.asInstanceOf[List[_]] else List(o)
  }
}
