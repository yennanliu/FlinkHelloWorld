package org.dev 

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.scala._

object LoadDataCSV {

  def main(args: Array[String]): Unit = {

    // set up execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val csvInput = env.readCsvFile[(String, String, String, String, String, String)]("FlinkHelloWorld/flink-project/data/sample2.csv")

    // filter csv
    val filtedcsv = csvInput
                    .filter( x => x._6.toFloat > 100 )
                    .filter( x => x._4 == x._5 )
    // group by 
    //val output = filtedcsv
    val output = filtedcsv
                .groupBy( x => ((x._2, x._3) , 1) )

   }
}