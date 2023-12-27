//import com.univocity.parsers.common.DataValidationException
//import org.joda.time.DateTime
import scala.math.Ordering.ordered

/**
 * @author ramezania
 *
 *        Algorithm To Compute The Gaps Between A Set of Intervals
 *         https://cs.stackexchange.com/questions/133276/algorithm-to-compute-the-gaps-between-a-set-of-intervals
 */
object FindGapBetweenMultpleRange {

  def main(args: Array[String]): Unit = {
    //    ranges = List((2, 10), (5, 7), (40, 50))
    var ranges0 = List(Lot1("L1",2,"START"), Lot1("L1",10,"STOP"), Lot1("L2",5,"START"), Lot1("L2",7,"STOP"), Lot1("L3",40,"START"), Lot1("L3",50,"STOP"))
    println(findGapsFlated[Lot1](ranges0, _.lotTimestamp , _.changeType==LotChangeType.STOP.toString))
    var ranges1 = List(Lot2(Some("L1"),Some(2),"START"), Lot2(Some("L1"),Some(10),"STOP"), Lot2(Some("L2"),Some(5),"START"), Lot2(Some("L2"),Some(7),"STOP"), Lot2(Some("L3"),Some(40),"START"), Lot2(Some("L3"),Some(50),"STOP"))
    println(findGapsFlated[Lot2](ranges1, _.lotTimestamp.get , _.changeType==LotChangeType.STOP.toString))
    println(findGapsFlatedTimestamp[Lot2](ranges1, _.lotTimestamp.get , _.changeType==LotChangeType.STOP.toString))

    //    ranges = List((0, 10), (20, 30), (40, 50))
    var ranges2 = List(Lot2(Some("L1"), Some(0), "START"), Lot2(Some("L1"), Some(10), "STOP"), Lot2(Some("L2"), Some(20), "START"), Lot2(Some("L2"), Some(30), "STOP"), Lot2(Some("L3"), Some(40), "START"), Lot2(Some("L3"), Some(50), "STOP"))
    println(findGapsFlated[Lot2](ranges2, _.lotTimestamp.get, _.changeType == LotChangeType.STOP.toString))
    println(measureGapsFlated[Lot2](ranges2, _.lotTimestamp.get, _.changeType == LotChangeType.STOP.toString))

    var ranges3 = List(LotOC(Some("L1"), Some(2), Some(10)), LotOC(Some("L1"), Some(5), Some(7)), LotOC(Some("L3"), Some(40), Some(50)))
    println(findGapsOpenClosed[LotOC](ranges3, _.startTimestamp.get, _.endTimestamp.get))
    println(measureGapsOpenClosed[LotOC](ranges3, _.startTimestamp.get, _.endTimestamp.get))

    ranges3 = List(LotOC(Some("L1"), Some(0), Some(10)), LotOC(Some("L1"), Some(20), Some(30)), LotOC(Some("L3"), Some(40), Some(50)))
    println(findGapsOpenClosed[LotOC](ranges3, _.startTimestamp.get, _.endTimestamp.get))
    println(measureGapsOpenClosed[LotOC](ranges3, _.startTimestamp.get, _.endTimestamp.get))

    var ranges4 = List((2, 10), (5, 7), (40, 50))
    println(findGapsOpenClosed[(Int,Int)](ranges4, _._1, _._2))
    println(measureGapsOpenClosed[(Int,Int)](ranges4, _._1, _._2))

    ranges4 = List((0, 10), (20, 30), (40, 50))
    println(findGapsOpenClosed[(Int,Int)](ranges4, _._1, _._2))
    println(measureGapsOpenClosed[(Int,Int)](ranges4, _._1, _._2))

    var rangesErr1 = List(/*Lot1("L1", 2, "START"),*/ Lot1("L1", 10, "STOP"), Lot1("L2", 5, "START"), Lot1("L2", 7, "STOP"), Lot1("L3", 40, "START"), Lot1("L3", 50, "STOP"))
    println(findGapsFlated[Lot1](rangesErr1, _.lotTimestamp, _.changeType == LotChangeType.STOP.toString))
    var rangesErr2 = List(Lot1("L1", 2, "START"), Lot1("L1", 10, "STOP"), Lot1("L2", 5, "START"), Lot1("L2", 7, "STOP"), Lot1("L3", 40, "START")/*, Lot1("L3", 50, "STOP")*/)
    println(findGapsFlated[Lot1](rangesErr2, _.lotTimestamp, _.changeType == LotChangeType.STOP.toString))
    var rangesErr3 = List(Lot1("L1", 2, "START"), Lot1("L1", 10, "STOP"), Lot1("L2", 5, "START"), Lot1("L2", 7, "STOP"), /*Lot1("L3", 40, "START"),*/ Lot1("L3", 50, "STOP"))
    println(findGapsFlated[Lot1](rangesErr3, _.lotTimestamp, _.changeType == LotChangeType.STOP.toString))
    var rangesErr4 = List(Lot1("L1", 2, "START"), Lot1("L1", 10, "STOP"), Lot1("L2", 5, "START"), /*Lot1("L2", 7, "STOP"),*/ Lot1("L3", 40, "START"), Lot1("L3", 50, "STOP"))
    println(findGapsFlated[Lot1](rangesErr4, _.lotTimestamp, _.changeType == LotChangeType.STOP.toString))
  }

/*
  def main1(args: Array[String]): Unit = {
    var ranges =List((2, 10), (5, 7), (40, 50))
      println(findGaps(ranges))

    ranges =List((0, 10), (20, 30), (40, 50))
    println(findGaps(ranges))

  }
*/

//  def findMissing(_min: Int, max: Int, ranges: List[(Int,Int)]):List[(Int,Int)] ={
//    var min=_min
//    ranges.reduce((x,y,i) => {
//      var res
//      if (y._1 < min) res = x
//      if (min < y._1) acc=(min, y._1 - 1)
//      if (i == ranges.length - 1 && max > y._2) acc=(y._2 + 1, max)
//      min = a._2 + 1
//      List(acc)
//    })
//  }

/*
  def findGaps(ranges: List[(Int,Int)]):List[(Int,Int)] ={
    var counter=0
    var prev= ranges.apply(0)._1
    var res = List[(Int,Int)]()
    ranges.flatMap(r => Array((r._1,'O'),(r._2,'C')).toIterator)
      .sortBy(_._1)
      .foreach(elem => {
        if (elem._2 == 'O'){
          if (counter == 0 && prev != elem._1) res ::= (prev, elem._1)
          counter += 1
          prev = -1
        }else if (elem._2 == 'C') {
          counter -= 1
          prev = elem._1
        }
        println(elem._1+","+elem._2.toString+","+counter+","+prev+";")
      })
    res
  }
*/

/*
  def findGapsFlated[L](ranges: List[L], timestampF: L => Long, isCloseF: L => Boolean)(implicit ord: Ordering[Long]): List[(Long, Long)] = {
    val sortedRanges = ranges.sortBy(timestampF)
    if (isCloseF(sortedRanges.apply(0))) throw new DataValidationException("Input ranges should start with Open/Start object not closed!")
    var prev = timestampF(sortedRanges.apply(0))
    var res = List[(Long, Long)]()
    var counter = 0
    sortedRanges.foreach(elem => {
      if (!isCloseF(elem)) {
        if (counter == 0 && prev != timestampF(elem)) res ::= (prev, timestampF(elem))
        counter += 1
        prev = Long.MinValue
      } else if (isCloseF(elem)) {
        counter -= 1
        prev = timestampF(elem)
      }
    })
    res.sortBy(_._1)
  }
*/

  /**
   * Algorithm to compute the gaps between a list of floating point numbers intervals like list( (start1,end1), (start2,end2), ... )
   * ex : List((0.2, 0.10), (0.5, 0.7), (0.40, 0.50)) => List((0.10,0.40))  &  List((0.0, 0.10), (0.20, 0.30), (0.40, 0.50)) => List( (0.10,0.20), (0.30,0.40))
   */
  def findGapsFlated[L](ranges: List[L], valueF: L => Double, isCloseF: L => Boolean)(implicit ord: Ordering[Double]): List[(Double, Double)] = {
    val sortedRanges = ranges.sortBy(valueF)
    //if (isCloseF(sortedRanges.apply(0))) throw new RuntimeException("Input ranges should start with Open/Start object not closed!")
    var prev = valueF(sortedRanges.apply(0))
    var res = List[(Double, Double)]()
    var counter = 0
    sortedRanges.foreach(elem => {
      if (!isCloseF(elem)) {
        if (counter == 0 && prev != valueF(elem)) res ::= (prev, valueF(elem))
        counter += 1
        prev = Double.MinValue
      } else if (isCloseF(elem)) {
        counter -= 1
        prev = valueF(elem)
      }
    })
    res.sortBy(_._1)
  }

  /**
   * Algorithm to measure the gaps between a list of floating point numbers intervals like list( (start1,end1), (start2,end2), ... )
   * ex : List((0.2, 0.10), (0.5, 0.7), (0.40, 0.50)) => 0.30  &  List((0.0, 0.10), (0.20, 0.30), (0.40, 0.50)) => 0.20
   */
  def measureGapsFlated[L](
    ranges:   List[L],
    valueF:   L => Double,
    isCloseF: L => Boolean): Double =
    findGapsFlated(ranges, valueF, isCloseF).map(r => r._2 - r._1).sum

  /**
   * Algorithm to compute the gaps between a list of  floating point numbers intervals like list( (start1,end1), (start2,end2), ... )
   * ex : List((0.2, 0.10), (0.5, 0.7), (0.40, 0.50)) => List((0.10,0.40))  &  List((0.0, 0.10), (0.20, 0.30), (0.40, 0.50)) => List( (0.10,0.20), (0.30,0.40))
   */
  def findGapsOpenClosed[L](
    ranges:      List[L],
    openValueF:  L => Double,
    closeValueF: L => Double): List[(Double, Double)] =
    findGapsFlated[(Double, Char)](ranges.flatMap(elm => Array((openValueF(elm), 'O'), (closeValueF(elm), 'C')).toIterator), _._1, _._2 == 'C')

  def measureGapsOpenClosed[L](
    ranges:      List[L],
    openValueF:  L => Double,
    closeValueF: L => Double): Double =
    findGapsOpenClosed(ranges, openValueF, closeValueF).map(r => r._2 - r._1).sum

  def findGapsFlatedTimestamp[L](
  ranges:     List[L],
  timestampF: L => Double,
  isCloseF:   L => Boolean): List[(Long, Long)] =
    findGapsFlated(ranges,timestampF, isCloseF).map(x => (x._1.toLong, x._2.toLong))

  def measureGapsFlatedTimestamp[L](
    ranges: List[L],
    timestampF: L => Double,
    isCloseF: L => Boolean): Long =
    findGapsFlatedTimestamp(ranges, timestampF, isCloseF).map(r => r._2 - r._1).sum

  def findGapsOpenClosedTimestamp[L](
    ranges:          List[L],
    openTimestampF:  L => Double,
    closeTimestampF: L => Double): List[(Long, Long)] =
    findGapsFlatedTimestamp[(Double, Char)](ranges.flatMap(elm => Array((openTimestampF(elm), 'O'), (closeTimestampF(elm), 'C')).toIterator), _._1, _._2 == 'C')

  def measureGapsOpenClosedTimestamp[L](
    ranges:          List[L],
    openTimestampF:  L => Double,
    closeTimestampF: L => Double): Long =
    findGapsOpenClosedTimestamp(ranges, openTimestampF, closeTimestampF).map(r => r._2 - r._1).sum

  def findGapsFlatedDatetime[L](
    ranges:    List[L],
    datetimeF: L => Double,
    isCloseF:  L => Boolean): List[(Long, Long)] =
    findGapsFlatedTimestamp(ranges, datetimeF, isCloseF).map(x => (x._1.toLong, x._2.toLong))

  def measureGapsFlatedDatetime[L](
    ranges:     List[L],
    datetimeF: L => Double,
    isCloseF:   L => Boolean): Long =
    findGapsFlatedDatetime(ranges, datetimeF, isCloseF).map(r => r._2 - r._1).sum

  def findGapsOpenClosedDatetime[L](
    ranges:          List[L],
    openDatetimeF:  L => Double,
    closeDatetimeF: L => Double): List[(Long, Long)] =
    findGapsFlatedDatetime[(Double, Char)](ranges.flatMap(elm => Array((openDatetimeF(elm), 'O'), (closeDatetimeF(elm), 'C')).toIterator), _._1, _._2 == 'C')

  def measureGapsOpenClosedDatetime[L](
    ranges:          List[L],
    openDatetimeF:  L => Double,
    closeDatetimeF: L => Double): Long =
    findGapsOpenClosedDatetime(ranges, openDatetimeF, closeDatetimeF).map(r => r._2 - r._1).sum
}

case class Lot1(
  lotId:            String,
  lotTimestamp:     Long,
  changeType:       String=LotChangeType.START.toString
){override def equals(obj: Any): Boolean = this.lotId == obj.asInstanceOf[Lot1].lotId && this.changeType == obj.asInstanceOf[Lot1].changeType}

case class Lot2(
  lotId:            Option[String],
  lotTimestamp:     Option[Long],
  changeType:       String=LotChangeType.START.toString
){override def equals(obj: Any): Boolean = this.lotId == obj.asInstanceOf[Lot2].lotId && this.changeType == obj.asInstanceOf[Lot2].changeType}

case class LotOC(
  lotId:            Option[String],
  startTimestamp:     Option[Long],
  endTimestamp:     Option[Long]
){override def equals(obj: Any): Boolean = this.lotId == obj.asInstanceOf[Lot2].lotId}

object LotChangeType extends Enumeration {
  val START, STOP = Value}
