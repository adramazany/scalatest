import java.io.StringWriter
import java.io.PrintWriter
import scala.collection.mutable.ListBuffer
import net.liftweb.json.JsonAST.JValue


/**
 * @author danoczim, bistakj
 */
object StringUtils {

  val FORMATTED_NEW_LINE = "\n\t\t\t"
  val SEPARATING_LINE = "\n-------------------------------------------------"
  val COLON = ":"
  val SEMICOLON = ";"
  val BACKTICK = "`"

  /**
   * Formats any throwable to nice String message with relevant stack trace formatted.
   *
   * @param throwable Any instance of exception in the form of <code>Throwable</code>.
   * @return Return nicely formatted message out of [[scala.Throwable]] instance.
   */
  def throwableToString(throwable: Throwable): String = {
    val stringWriter = new StringWriter
    throwable.printStackTrace(new PrintWriter(stringWriter))
    stringWriter.toString()
  }

  /**
   * Pretty prints a Scala value similar to its source representation.
   * Particularly useful for case classes.
   *
   * @param record          The value to string.
   * @param indentSize      Number of spaces for each indent.
   * @param maxElementWidth Largest element size before wrapping.
   * @param depth           Initial depth to pretty print indents.
   * @return
   */
  def toString(
    record:          Any,
    indentSize:      Int = 2,
    maxElementWidth: Int = 400,
    depth:           Int = 8): String = {
    val indent = " " * depth * indentSize
    val fieldIndent = indent + (" " * indentSize)
    record match {
      case null => "NULL"
      case jsonValue: JValue => jsonValue.values match {
        case null => "NULL"
        case _ => jsonValue.values.toString
      }
      case emptySequence: Seq[_] if emptySequence.isEmpty => emptySequence.toString
      case sequence: Seq[_] =>
        // If the Seq is not too long, pretty print on one line.
        val resultOneLine = sequence.map(toString(_, indentSize, maxElementWidth, depth + 1)).toString
        if (resultOneLine.length <= maxElementWidth) return resultOneLine
        // Otherwise, build it with newlines and proper field indents.
        val result = sequence.map(x => s"\n$fieldIndent" + toString(x, indentSize, maxElementWidth, depth + 1)).toString
        result.substring(0, result.length - 1) + "\n" + indent + ")"
      case optional: Option[_] => toString(optional.getOrElse(""), indentSize, maxElementWidth, depth + 1)
      case array: Array[_] =>
        val result = new ListBuffer[String]()
        array.foreach(item => result += toString(item, indentSize, maxElementWidth, depth + 1))
        s"\n$fieldIndent[" + result.mkString(s",\n$fieldIndent") + "]"
      // Product should cover case classes.
      case product: Product =>
        val prefix = fieldIndent + product.productPrefix
        // We'll use reflection to get the constructor arg names and values.
        val fields = product.getClass.getDeclaredFields.filterNot(_.isSynthetic).map(_.getName)
        val values = product.productIterator.toSeq
        // If we weren't able to match up fields/values, fall back to toString.
        if (fields.length != values.length) return product.toString
        fields.zip(values).toList match {
          // If there are no fields, just use the normal String representation.
          case Nil => product.toString
          // If there is just one field, let's just print it as a wrapper.
          case (_, value) :: Nil => s"$prefix(" + toString(value, indentSize, maxElementWidth, depth) + ")"
          // If there is more than one field, build up the field names and values.
          case keyValues =>
            val prettyFields = keyValues.map {
              case (k, v) => s"$fieldIndent" + toString(k, indentSize, maxElementWidth, depth + 1) + " = " + toString(v, indentSize, maxElementWidth, depth + 1)
            }
            s"$prefix(\n${prettyFields.mkString(",\n")}\n$fieldIndent)"
        }
      // If we haven't specialized this type, just use its toString.
      case _ => record.toString
    }
  }

  /**
   * This function encloses each string in array by given delimeter.
   */
  def encloseStringsInArray(
    delimeter:      String,
    arrayToDelimit: Array[String]): Array[String] = {
    arrayToDelimit.map(stringToEnclose => delimeter + stringToEnclose + delimeter)
  }

  /**
   * Gets optional entity as sequence of it.
   */
  def getAsSeq(entity: Option[String]): Seq[String] =
    if (entity.isDefined) Seq[String](entity.get) else Seq[String]()

  /**
   * Extracts name of class from its name ending with $.
   */
  implicit class StringExtension(val string: String) {
    def extractClassName = {
      string.split("\\$").last
    }

    /**
     * Determine if a string is integer number.
     */
    def isInt: Boolean = string.matches("[-+]?\\d+?")

    /**
     * Converts a camel cased identifier into underscore.
     */
    def convertCamelNamingToUnderscore = {
      "[A-Z\\d]".r.replaceAllIn(string, regexRep => "_" + regexRep.group(0).toLowerCase())
    }

    /**
     * Converts a underscore into camel cased.
     */
    def convertUnderscoreNamingToCamel = {
      "_([a-z\\d])".r.replaceAllIn(string, { m => m.group(1).toUpperCase()
      })
    }

    /**
     * Formats input string by applying pattern and parameters and escape $ characters.
     *
     * @param pattern Regular expression.
     * @param vars    Repeated parameters, which should be included in regular expression.
     * @return Formatted string.
     */
    def formatByPattern(
      pattern:    String,
      parameters: Array[String]): String = {
      var index = -1
      for (i <- 0 until parameters.length) {
        parameters(i) = parameters(i).replaceAll("\\$", "\\\\\\$")
      }
      pattern.r.replaceSomeIn(string, helper => {
        index += 1
        parameters.lift(index)
      })
    }
  }
}