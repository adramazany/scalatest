/**
 * @author ramezania
 */
object TestEnumDefaultValue {
  def main(args: Array[String]):Unit = {
    println("TestEnumDefaultValue starting...")

    println("LotConstants.NO_LOT_NAME="+LotConstants.NO_LOT_NAME)
    println("LotConstants.NO_LOT_ID="+LotConstants.NO_LOT_ID)
    println("LotConstants.EMPTY_LOT_NAME="+LotConstants.EMPTY_LOT_NAME)
    println("LotConstants.EMPTY_LOT_ID="+LotConstants.EMPTY_LOT_ID)

    println("TestEnumDefaultValue finished.")
  }
}

object LotConstants extends Enumeration {

  val NO_LOT_NAME = Value
  val NO_LOT_ID = Value

  // Empty string for lot name during migration
  val EMPTY_LOT_NAME = Value("")

  //Empty string for lot id during migration
  val EMPTY_LOT_ID = Value("")
}