import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.ArrayType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import scala.collection.Seq
import DataFrameFunctions.toDataFrameFunctions

/**
 * @author ramezania
 */
object ReplaceMultiColumnWithListColumnInDF {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("MySparkSession")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.sqlContext.implicits._

    val data = List(Root(category= "A", id= 1, value= 121.44, truth= true),
                  Root(category= "B", id= 2, value= 300.01, truth= false),
                  Root(category= "C", id= 3, value= 10.99,  truth= false),
                  Root(category= "E", id= 4, value= 33.87,  truth= true))
//    val df = spark.createDataFrame(data)
    val df = data.toDF
    println(df.schema)
    df.show(false)

    val data2 = List(Root2(id= 1, value= 121.44, cat_list=List(Cat("A",true))),
                     Root2(id= 2, value= 300.01, cat_list=List(Cat("B",false),Cat("B1",false))),
                     Root2(id= 3, value= 10.99,  cat_list=List(Cat("C",false))),
                     Root2(id= 4, value= 33.87,  cat_list=List()))
//    val df2 = spark.createDataFrame(data2)
    val df2 = data2.toDF
    println(df2.schema)
    df2.show(false)

//    val df3 = df.withColumn("cat_list", typedLit(Cat(df.col("category"),df.col("truth"))))
    val sqlfunc = udf((category: String,truth: Boolean) => Seq(Cat(category,truth)))
    val df3 = df.withColumn("catList", sqlfunc(col("category"),col("truth")))
      .drop("category","truth")
      .convertFieldNamingToUnderscore(spark)
    println(df3.schema)
    df3.show(false)

  }

}
case class Root (category: String, id: Int, value: Double, truth: Boolean)
case class Cat(cateGory: String, trUth: Boolean)
case class Root2 (id: Int, value: Double, cat_list: Seq[Cat])

object DataFrameFunctions{
  implicit class StringExtension(val string: String) {
    def convertUnderscoreNamingToCamel = {
      "_([a-z\\d])".r.replaceAllIn(string, { m => m.group(1).toUpperCase()
      })
    }
    def convertCamelNamingToUnderscore = {
      "[A-Z\\d]".r.replaceAllIn(string, {regexRep => "_" + regexRep.group(0).toLowerCase()})
    }
  }
  implicit class toDataFrameFunctions(val dataFrame: Dataset[Row]) {
    private def renameSchemaColumnNames(
      schema:        StructType,
      transformFunc: String => String): StructType = {
      def recursiveRenameSchema(schema: StructType): Seq[StructField] = schema.fields.map {
        case StructField(name, dataType: StructType, nullable, meta) =>
          StructField(transformFunc(name), StructType(recursiveRenameSchema(dataType)), nullable, meta)
        case StructField(name, dataType: ArrayType, nullable, meta) if dataType.elementType.isInstanceOf[StructType] =>
          StructField(transformFunc(name), ArrayType(StructType(recursiveRenameSchema(dataType.elementType.asInstanceOf[StructType])), true), nullable, meta)
        case StructField(name, dtype, nullable, meta) =>
          StructField(transformFunc(name), dtype, nullable, meta)
      }
      StructType(recursiveRenameSchema(schema))
    }

    def convertFieldNamingToCamel(sparkSession: SparkSession): Dataset[Row] =
      sparkSession.sqlContext.createDataFrame(dataFrame.rdd, renameSchemaColumnNames(dataFrame.schema, _.convertUnderscoreNamingToCamel))
    def convertFieldNamingToUnderscore(sparkSession: SparkSession): Dataset[Row] =
      sparkSession.sqlContext.createDataFrame(dataFrame.rdd, renameSchemaColumnNames(dataFrame.schema, _.convertCamelNamingToUnderscore))

  }
}

