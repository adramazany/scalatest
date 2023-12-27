
//import scala.collection.mutable.{Map=>HashMap}
import scala.collection.mutable.{ Map => MutableMap }
/**
 * @author ramezania
 */
object TestDataLostOutsideForeach {
  def main(args: Array[String]): Unit = {
//    val am = HashMap( 1->X("A"), 3->X("C"), 4->X("D") )
//    val bm = HashMap( 1->X("AAA"), 2->X("BBB"), 4->X("DDD") )
    val am = MutableMap( 1->X("A"), 3->X("C"), 4->X("D") )
    val bm = MutableMap( 1->X("AAA"), 2->X("BBB"), 4->X("DDD") )

    println("am="+am)
    println("bm="+bm)

    am.foreach(a => {
      val b = bm.getOrElseUpdate(a._1,a._2)
      b.text= b.text + "1"
    })

    println("bm="+bm)

  }
}
case class X (var text:String)
