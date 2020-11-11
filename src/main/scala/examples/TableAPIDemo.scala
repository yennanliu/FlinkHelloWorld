package examples

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

  //val tableEnv = TableEnvironment.create(fsSettings)

  // val csvSource:TableSource = new CsvTableSource("/data/test.csv")
  //
  // tableEnv.registerTableSource("CsvTable", csvSource)
}
