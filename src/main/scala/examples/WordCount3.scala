package examples

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

// https://www.youtube.com/watch?v=7xlz1NVn76U&list=PLmOn9nNkQxJGLnTsoWaHfvXrfpWiihoxV&index=8

object WordCount3 extends App {
  // get a run env
  val env = ExecutionEnvironment.getExecutionEnvironment

  // load data from file
  val inputPath = "src/main/resources/test.txt"
  val inputDataSet = env.readTextFile(inputPath)

  // wordcount
  // need to "org.apache.flink.api.scala._" for scala implicit transformation
  val wordCountDataSet = inputDataSet.flatMap(_.split("\n"))
    .map( (_, 1) )
    .groupBy(0)  // group by index = 0 (key)
    .sum(1) // sum by index = 1 (value)

  wordCountDataSet.print()
}
