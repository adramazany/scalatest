
import java.sql.DriverManager
import java.sql.Connection

/**
 * @author ramezania
 */
object TestStmtAddBatchClearBatch {
  val driver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://localhost/muehlbauer.factoryleader"
  val username = "postgres"
  val password = "admin"
  var id: Int = 100


  def main(args: Array[String]): Unit = {
    test_batch
    test_select

    connection.close()
  }

  def test_batch(): Unit = {
    val ps = getCN().prepareStatement("INSERT INTO test.t1 VALUES (?, ?)")
    ps.setInt(1, id)
    ps.setString(2, "Doe")
    ps.addBatch

    ps.setInt(1, id+1)
    ps.setString(2, "Smith")
    ps.addBatch

    var results = ps.executeBatch
    println(results)

    ps.setInt(1, id + 3)
    ps.setString(2, "Test duplication")
    ps.addBatch

    results = ps.executeBatch
    println(results)
  }

  def test_select(): Unit = {
    try {
      // create the statement, and run the select query
      val statement = getCN().createStatement()
      val resultSet = statement.executeQuery("SELECT id,text FROM test.t1")
      while (resultSet.next()) {
        val id = resultSet.getString("id")
        val text = resultSet.getString("text")
        println("id, text = " + id + ", " + text)
      }
    } catch {
      case e => e.printStackTrace
    }
    //connection.close()
  }

  var connection: Connection = null
  def getCN(): Connection = {
    if(connection==null){
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
    }
    return connection
  }
}
