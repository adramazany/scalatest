object TestFieldIsListOrNot {

  def main(args: Array[String]): Unit = {
    println(convertToList( 1 ))
    println(convertToList( 1.1111 ))
    println(convertToList( "ali" ))
    println(convertToList( (1,2,3) ))
    println(convertToList( Array(4,5,6) ))
    println(convertToList( List(7,8,9) ))
    println(convertToList( Seq(10,11,12) ))
    println(convertToList( TestFieldIsListOrNot ))
  }
  def convertToList(x: Any): List[_] = {
    println(x.getClass+" , "+x)
    x match {
      case x: Int => List(x)
      case x: Double => List(x)
      case x: String => List(x)
      case x: List[_] => x
      case x: Array[_] => x.toList
      case x: scala.Product => x.productIterator.toList
      case x: Seq[_] => x.toList
      case _ => throw new IllegalArgumentException
    }
  }
  def isArrayOrNot(x: Any): Boolean = {
      x match{
        case x: List[_] => true
        case x: Array[_] => true
        case x: Seq[_] => true
        case _ => false
      }
  }
}
