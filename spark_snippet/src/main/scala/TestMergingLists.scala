import scala.collection.immutable.Map

/**
 * @author ramezania
 */
object TestMergingLists {
  def main(args: Array[String]): Unit = {
    var currentMachineStatusMap = Map[String, CurrentMachineStatus2]("m0" -> CurrentMachineStatus2("m0", Some(List[Lot](Lot("l0", "L0", 0))))
                                                                   ,"m1" -> CurrentMachineStatus2("m1", Some(List[Lot](Lot("l1", "L1", 1), Lot("l10", "L10", 10))))
                                                                   ,"m2" -> CurrentMachineStatus2("m2", Some(List[Lot](Lot("l2", "L200", 200), Lot("l20", "L20", 20)))))
    val currentLotMachineRunMap = Map[String, List[CurrentLotMachineRun]]("m1"-> List(CurrentLotMachineRun("m1", "l1", "L1", 110),CurrentLotMachineRun("m1", "l11", "L11", 111))
                                                                         ,"m2"-> List(CurrentLotMachineRun("m2", "l2", "L2", 2),CurrentLotMachineRun("m2", "l22", "L22", 22))
                                                                         ,"m3"-> List(CurrentLotMachineRun("m3", "l3", "L3", 3)))

    println("currentMachineStatusMap=" + currentMachineStatusMap)
    println("currentLotMachineRunMap=" + currentLotMachineRunMap)

    val currentLotMachineRunMap__currentMachineStatusMap =
    currentLotMachineRunMap
      .groupBy(_._1)
      .flatMap(y => y._2.get(y._1).get)
      .map(x => x.machineId -> Lot(x.lotId, x.lotName, x.startTimestamp))
      .groupBy(_._1)
      .map(x => x._1 -> CurrentMachineStatus2(x._1, Some(x._2.map(a => a._2).toList)))
      .toMap
//out: result=Map(
// m0 -> CurrentMachineStatus2(m0,Some(List(Lot(l0,L0,0)))),
// m1 -> CurrentMachineStatus2(m1,Some(List(Lot(l1,L1,110), Lot(l11,L11,111)))),
// m2 -> CurrentMachineStatus2(m2,Some(List(Lot(l2,L2,2), Lot(l22,L22,22)))),
// m3 -> CurrentMachineStatus2(m3,Some(List(Lot(l3,L3,3)))))
    println("currentLotMachineRunMap__currentMachineStatusMap=" + currentLotMachineRunMap__currentMachineStatusMap)
    println("result=" + (currentMachineStatusMap.toSet ++ currentLotMachineRunMap__currentMachineStatusMap.toSet))

    currentLotMachineRunMap__currentMachineStatusMap
      .foreach(x => {
        var a = currentMachineStatusMap.getOrElse(x._1,CurrentMachineStatus2(x._1, Some(List[Lot]())))
        a.lots = Some((x._2.lots.get ++ a.lots.get).toSet.toList)
        currentMachineStatusMap += (x._1->a)
      })
//out: merge=Map(m0 -> CurrentMachineStatus2(m0,Some(List(Lot(l0,L0,0)))),
// m1 -> CurrentMachineStatus2(m1,Some(List(Lot(l1,L1,110), Lot(l11,L11,111), Lot(l10,L10,10)))),
// m2 -> CurrentMachineStatus2(m2,Some(List(Lot(l2,L2,2), Lot(l22,L22,22), Lot(l20,L20,20)))),
// m3 -> CurrentMachineStatus2(m3,Some(List(Lot(l3,L3,3)))))
    println("merge=" + (currentMachineStatusMap))
  }
}

case class CurrentMachineStatus2(
  machineId:                 String,
  var lots:                  Option[List[Lot]]
  )