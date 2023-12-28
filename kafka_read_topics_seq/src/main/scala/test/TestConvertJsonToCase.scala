package bip.kafka.seqtopics
package test

import bip.kafka.seqtopics.biz.D1Functions
import bip.kafka.seqtopics.domain.D1
import bip.kafka.seqtopics.domain.D2
import bip.kafka.seqtopics.domain.LotChangedEvent
import scala.util.Try
import scala.util.Try

/**
 * @author ramezania
 */
object TestConvertJsonToCase {

  implicit val formats = net.liftweb.json.DefaultFormats

  def main(args: Array[String]): Unit = {
//    simple()
//    extract3[LotChangedEvent,D1,D2](D1.json)
    extract3callback[LotChangedEvent,D1,D2](D1.json,D1Functions.apply)
  }

  def extract1[A: Manifest](json: String): Unit = {
    print(Try(net.liftweb.json.parse(json).extract[A]))
  }

  def extract3[A:Manifest, B:Manifest, C: Manifest](json: String): Unit = {
    print(Try(net.liftweb.json.parse(json).extract[B]))
  }

  def extract3callback[A:Manifest, B:Manifest, C: Manifest](json: String, callbackB: (B)=>Unit): Unit = {
    val obj:B = Try(net.liftweb.json.parse(json).extract[B]).get
    print(obj)
    callbackB(obj)
  }

  def simple(): Unit = {
    print(net.liftweb.json.parse(D1.json).extract[D1])
  }
}
