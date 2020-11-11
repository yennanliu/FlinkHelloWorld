package examples

import java.sql.Types

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.collector.selector.OutputSelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

//import org.apache.flink.table.api.EnvironmentSettings
//import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
//import org.apache.flink.api.scala.ExecutionEnvironment
//import org.apache.flink.table.api.bridge.scala.BatchTableEnvironment

// https://ci.apache.org/projects/flink/flink-docs-stable/dev/table/common.html#create-a-tableenvironment

object TableAPIDemo extends App{
  println ("TableAPIDemo run...")

  val env = StreamExecutionEnvironment.getExecutionEnvironment

  // TODO : fix import errors, fix syntax errors

  // 1. register a table via TableSource

  //val tableEnv = TableEnvironment.create(fsSettings)

  // val csvSource:TableSource = new CsvTableSource("/data/test.csv")

  // tableEnv.registerTableSource("CsvTable", csvSource)

  // 2. save data to outside source via TableSink

  //  val tableEnv2 = TableEnvironment.getTableEnvironment(env)
  //
  //  val csvSink:Tablesink = new CsvTableSink("/output/output.csv")
  //
  //  val fieldName:Array[String] = Array("a", "b", "c")
  //
  //  val fieldType:Array[TypeInformation[_]] = Array(Types.INTEGER, Types.STING, Types.LONG)
  //
  //  tableEnv2.registerTableSink("csvTable", fieldName, fieldType, csvSink)
  }
