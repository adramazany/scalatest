import scala.collection.mutable.HashMap
import scala.collection.JavaConverters.mapAsJavaMap
import scala.collection.JavaConverters.asJavaCollectionConverter
import scala.collection.JavaConverters.asJavaCollection
import scala.collection.JavaConverters._

/**
 * @author ramezania
 */
object TestMapGetOrElse {
  def main(args: Array[String]): Unit = {
    val m = HashMap("1"->"A" , "2"->"B")
    println(m)
    m.getOrElseUpdate("1", "-")
    m.getOrElseUpdate("3", "-")
    println(m)
  }
}

