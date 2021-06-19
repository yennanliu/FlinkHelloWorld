package basicExamples

// https://www.youtube.com/watch?v=BpwIQMDvptM&list=PLmOn9nNkQxJGLnTsoWaHfvXrfpWiihoxV&index=10

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

object WordCount5 extends App {

  println("WordCount5 start ...")

  // get env
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  // load data (from socket)
  val dataStream = env.socketTextStream("localhost", 7777)

  // process data (stream)
  /**  each operation can set parallelism (setParallelism()), if not setting, use default one  */
  val wordCountDataStream = dataStream.flatMap(_.split(" "))
    .filter(_.nonEmpty).setParallelism(2)
    .map((_, 1)).setParallelism(3)
    .keyBy(0)
    .sum(1)

  wordCountDataStream.print().setParallelism(2) // we can set the Parallelism for this op here (default Parallelism is core of the machine)

  // execute the executor
  env.execute()

  println("WordCount5 end ...")
}
