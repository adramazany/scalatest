object ConvertListOfStringsToCassandraString {

  def main(args: Array[String]):Unit = {
    println( convertListOfStringsToCassandraString( List("aa","1","bb","2") ) )
    println( convertListOfStringsToCassandraString( List("aa","1","bb","") ) )
    println( convertListOfStringsToCassandraString( List("aa","1","",null) ) )
    println( convertListOfStringsToCassandraString( List("",null) ) )

    println( convertListOfStringsToCassandraString2( List("aa","1","") ) )
  }

  def convertListOfStringsToCassandraString(`list`: List[String]): String = {
    "[" + `list`.filter(element => element != null && !removeForbiddenCharactersFromStringParameter(element).isEmpty)
      .map(element => "'" + removeForbiddenCharactersFromStringParameter(element) + "'")
      .mkString(",") + "]"
//      .reduce(_ + "," + _) + "]"
  }

  def convertListOfStringsToCassandraString2(`list`: List[String]): String = {
    "[" + `list`.filter(text => !text.isEmpty)
      .map(element => "'" + removeForbiddenCharactersFromStringParameter(element) + "'")
      .mkString(",") + "]"
//      .reduce(_ + "," + _) + "]"
  }

  def removeForbiddenCharactersFromStringParameter(text: String): String = {
    text.replace("'", "''")
  }
}
