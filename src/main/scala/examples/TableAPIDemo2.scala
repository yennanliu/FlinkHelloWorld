//package examples
//
//// TOFIX : import org.apache.flink.table
//
//import org.apache.flink.api.common.typeinfo.TypeInformation
//import org.apache.flink.api.scala.typeutils.Types
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.table.api.TableEnvironment
//import org.apache.flink.table.sources.CsvTableSource
//
//object TableAPIDemo2 extends App {
//
//  val sEnv = StreamExecutionEnvironment.getExecutionEnvironment
//  val sTableEnv = TableEnvironment.getTableEnvironment(sEnv)
//
//  // implicit transformation
//
//  import org.apache.flink.api.scala._
//
//  // create a TableSource
//  val csvSource = new CsvTableSource("data/example.csv",
//    Array[String]("name", "age"),
//    Array[TypeInformation[_]](Types.STRING, Types.INT))
//
//  // register a table named "csvTable"
//  sTableEnv.registerTableSource("csvTable", csvSource)
//
//  val csvTable = sTableEnv.scan("csvTable")
//  val csvResult = csvTable.select("name", "age")
//
//  // define Student dtype
//  case class Student(name: String, age: Int)
//
//  val csvStream = sTableEnv.toAppendStream[Student](csvResult)
//  csvStream.print.setParallelism(1)
//
//  // run
//  sEnv.execute("csvStream")
//
//}
