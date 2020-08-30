// package org.dev 

// import org.apache.flink.api.scala._
// import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
// import org.apache.flink.table.api.scala._

// object LoadDataSet {

//   def main(args: Array[String]): Unit = {

//     // set up execution environment
//     val env = StreamExecutionEnvironment.getExecutionEnvironment
//     val tEnv = StreamTableEnvironment.create(env)

//     val dataSet = tenv.readTextFile("data/sample.json")

//     dataSet.collect()

//    }
// }