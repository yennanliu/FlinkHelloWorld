// package org.dev 

// import org.apache.flink.api.scala._
// import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
// import org.apache.flink.table.api.scala._

// object LoadDataAsStream {

//   def main(args: Array[String]): Unit = {

//     // set up execution environment
//     val env = StreamExecutionEnvironment.getExecutionEnvironment
//     val tEnv = StreamTableEnvironment.create(env)

//     val filesource = "data/sample.json"

//     val dataSet = tenv.readTextFile(filesource)

//     dataSet.collect()

//     val dataSet2 = tenv.readTextFile(filesource)
//         .withSchema(
//           new Schema()
//             .field("MyField1", Types.INT)     // required: specify the fields of the table (in this order)
//             .field("MyField2", Types.STRING)
//             .field("MyField3", Types.BOOLEAN)
//     )

//     val wordCounts = dataSet
//             .flatMap{ _.split(" ") map { (_, 1) } }
//             .keyBy(0)
//             .timeWindow(Time.seconds(5))
//             .sum(1).setParallelism(5)



    
//     wordCounts.print()

//    }
// }