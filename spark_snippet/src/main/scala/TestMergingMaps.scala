import scala.collection.immutable.Map

/**
 * @author ramezania
 */
object TestMergingMaps {
  def main(args: Array[String]): Unit = {
    val currentMachineStatusMap = Map[String, CurrentMachineStatus]("m0" -> CurrentMachineStatus("m0", Some(Map[String, Lot]("l0" -> Lot("l0", "L0", 0))))
                                                                   ,"m1" -> CurrentMachineStatus("m1", Some(Map[String, Lot]("l1" -> Lot("l1", "L1", 1), "l10" -> Lot("l10", "L10", 10))))
                                                                   ,"m2" -> CurrentMachineStatus("m2", Some(Map[String, Lot]("l2" -> Lot("l2", "L200", 200), "l20" -> Lot("l20", "L20", 20)))))
    val currentLotMachineRunMap = Map[String, List[CurrentLotMachineRun]]("m1"-> List(CurrentLotMachineRun("m1", "l1", "L1", 110),CurrentLotMachineRun("m1", "l11", "L11", 111))
                                                                         ,"m2"-> List(CurrentLotMachineRun("m2", "l2", "L2", 2),CurrentLotMachineRun("m2", "l22", "L22", 22))
                                                                         ,"m3"-> List(CurrentLotMachineRun("m3", "l3", "L3", 3)))

    println("currentMachineStatusMap=" + currentMachineStatusMap)
    println("currentLotMachineRunMap=" + currentLotMachineRunMap)

//    val currentLotMachineRunMap__currentMachineStatusMap = currentLotMachineRunMap.map(clmr => clmr._1 -> CurrentMachineStatus("m1", Some(Map[String, Lot]("l1" -> Lot("l1", "L1", 111))))).toMap
//out: currentLotMachineRunMap__currentMachineStatusMap=Map(m1 -> CurrentMachineStatus(m1,Some(Map(l1 -> Lot(l1,L1,111)))))
//    val currentLotMachineRunMap__currentMachineStatusMap = currentLotMachineRunMap.groupBy(m=>m._1).get("m1").get.map(x => x.lotId -> Lot(x.lotId, x.lotName, x.startTimestamp)).toMap
//out: currentLotMachineRunMap__currentMachineStatusMap=Map(l1 -> Lot(l1,L1,110), l11 -> Lot(l11,L11,111))
//    val currentLotMachineRunMap__currentMachineStatusMap =
//      currentLotMachineRunMap.groupBy(_._1).flatMap(y => y._2.get(y._1).get).map(x => x.lotId -> Lot(x.lotId, x.lotName, x.startTimestamp)).toMap
//out: currentLotMachineRunMap__currentMachineStatusMap=Map(l1 -> Lot(l1,L1,110), l11 -> Lot(l11,L11,111))
//    val currentLotMachineRunMap__currentMachineStatusMap =
//      currentLotMachineRunMap
//        .groupBy(_._1)
//        .flatMap(y => y._2.get(y._1).get)
//        .map(x => x.lotId -> Lot(x.lotId, x.lotName, x.startTimestamp,x.machineId))
//        .groupBy(_._2.machineId)
//        .map(x=> x._1 -> CurrentMachineStatus(x._1, Some(x._2.toMap)))
//        .toMap
//out: result=Map(
// m0 -> CurrentMachineStatus(m0,Some(Map(l0 -> Lot(l0,L0,0,))))
// , m1 -> CurrentMachineStatus(m1,Some(Map(l1 -> Lot(l1,L1,110,m1), l11 -> Lot(l11,L11,111,m1))))
// , m2 -> CurrentMachineStatus(m2,Some(Map(l2 -> Lot(l2,L2,2,m2), l22 -> Lot(l22,L22,22,m2))))
// , m3 -> CurrentMachineStatus(m3,Some(Map(l3 -> Lot(l3,L3,3,m3)))))
    val currentLotMachineRunMap__currentMachineStatusMap =
    currentLotMachineRunMap
      .groupBy(_._1)
      .flatMap(y => y._2.get(y._1).get)
      .map(x => (x.machineId,x.lotId) -> Lot(x.lotId, x.lotName, x.startTimestamp))
      .groupBy(_._1._1)
      .map(x => x._1 -> CurrentMachineStatus(x._1, Some(x._2.map(a => a._2.lotId->a._2).toMap)))
      .toMap
//out: result=Map(
// m0 -> CurrentMachineStatus(m0,Some(Map(l0 -> Lot(l0,L0,0)))),
// m1 -> CurrentMachineStatus(m1,Some(Map(l1 -> Lot(l1,L1,110), l11 -> Lot(l11,L11,111)))),
// m2 -> CurrentMachineStatus(m2,Some(Map(l2 -> Lot(l2,L2,2), l22 -> Lot(l22,L22,22)))),
// m3 -> CurrentMachineStatus(m3,Some(Map(l3 -> Lot(l3,L3,3)))))
    println("currentLotMachineRunMap__currentMachineStatusMap=" + currentLotMachineRunMap__currentMachineStatusMap)
    println("result=" + (currentMachineStatusMap++currentLotMachineRunMap__currentMachineStatusMap))

  }
}


case class CurrentMachineStatus(
  machineId:                 String,
  var lots:                  Option[Map[String,Lot]],
  )

case class Lot(
  lotId:        String,
  var lotName:      String,
  var lotTimestamp: Long,
//  machineId:String=""
){
  override def equals(obj: Any): Boolean = this.lotId == obj.asInstanceOf[Lot].lotId
}

case class CurrentLotMachineRun(
  machineId:          String,
  lotId:              String,
  var lotName:        String,
  var startTimestamp: Long)
