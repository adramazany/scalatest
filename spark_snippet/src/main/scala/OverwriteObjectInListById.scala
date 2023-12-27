/**
 * @author ramezania
 */
object OverwriteObjectInListById {
  def main(args: Array[String]): Unit = {
    var l1 = List(M1(1,"A"),M1(2,"B"),M1(3,"C"))
    var s1:Set[M1] = l1.toSet
    println(l1)
    l1 = l1 ++ List(M1(4,"D"))
    s1 = s1 ++ Set(M1(4,"D"))
    println(l1)
    println(s1)

    l1=List(M1(1,"A111"))++l1
    l1=M1(2,"B222")::l1
    l1=l1.+:(M1(3,"C333")).toSet.toList

    s1=Set(M1(1, "A111"))++s1
    s1=s1.+(M1(3,"C333"))
    println(l1)
    println(s1)

  }
}
case class M1 (id:Int, name:String){
  override def equals(obj: Any): Boolean = this.id==obj.asInstanceOf[M1].id
}
