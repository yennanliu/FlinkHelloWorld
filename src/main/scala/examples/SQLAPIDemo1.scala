//package examples
//
//import org.apache.flink.api.common.typeinfo.TypeInformation
//import org.apache.flink.api.scala.ExecutionEnvironment
//import org.apache.flink.api.scala.typeutils.TypeUtils
//import org.apache.flink.core.fs.FileSystem
//import org.apache.flink.table.api.TableEnvironment
//import org.apache.flink.table.sink.CsvTableSink
//
//// TODO : FIX IMPORT AND OTHER ERRORS
//object SQLAPIDemo1 extends App{
//
//  val bEnv = ExecutionEnvironment.getExecutionEnvironment
//
//  val bTableEnv = TableEnvironment.getTableEnvironment(bEnv)
//
//  // implicit transformation
//  import org.apache.flink.api.scala._
//
//  val dataSource = bEnv.readTextFile("data/example.txt")
//
//  case class Student (name:String, age:Int)
//
//  val inputData = dataSource
//    .map(line => {
//      val splits = line.split(",")
//      val stu = new Student(splits(0), splits(1).toInt),
//      stu
//    })
//
//  // DataSet -> table
//  val table = bTableEnv.fromDataSet(inputData)
//
//  // register "Student" table
//  bTableEnv.registerTable("student", table)
//
//  // run the SQL query
//  val query =
//    """
//      |SELECT
//      |COUNT(1),
//      |avg(age)
//      |from
//      |student
//    """.stripMargin
//
//  val sqlQuery = bTableEnv.sqlQuery(query)
//
//  // create CsvTableSink
//  val CsvTableSink = new CsvTableSink("data/result.csv", ".". 1. FileSystem.WriteMode.OVERWRITE)
//
//  // register TableSink
//  bTableEnv.registerTableSink(
//    "csvOutputTable",
//    Array[String]("count", "avg_age"),
//    Array[TypeInformation[_]](Types.LONG, Types.INT), csvTableSink)
//
//  // save the results to the csvTableSink table
//  sqlQuery.insert("csvTableSink")
//  bEnv.execute("SQL-BATCH")
//}
