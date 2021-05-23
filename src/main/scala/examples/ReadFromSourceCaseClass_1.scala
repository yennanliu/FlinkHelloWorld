package examples

// https://www.youtube.com/watch?v=LqGnkljfZR4&list=PLmOn9nNkQxJGLnTsoWaHfvXrfpWiihoxV&index=16

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment}

// define case class : for user defined data schema
case class SensorRecord(
              is: String,
              timestamp: Long,
              temperature : Double
                       )

object ReadFromSourceCaseClass_1 extends App {
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  val stream1 = env.fromCollection(
    List(
    SensorRecord("s1", 154778199, 43.54543),
    SensorRecord("s2", 154778199, 45.534),
    SensorRecord("s3", 14378199, 50.54334),
    SensorRecord("s1", 154754199, 100.54334)
      )
  )

  stream1.print("stream1").setParallelism(1)
  //stream1.print("stream1").setParallelism(5)

  env.execute("ReadFromSourceCaseClass_1")
}
