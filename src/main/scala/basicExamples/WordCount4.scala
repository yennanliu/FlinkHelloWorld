package basicExamples

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

object WordCount4 extends App {

  println("WordCount4 start ...")

  // get env
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  // load data (from socket)
  val dataStream = env.socketTextStream("localhost", 7777)

  // process data (stream)
  val wordCountDataStream = dataStream.flatMap(_.split(" "))
    .filter(_.nonEmpty)
    .map((_, 1))
    .keyBy(0)
    .sum(1)

  wordCountDataStream.print().setParallelism(2) // we can set the Parallelism for this op here (default Parallelism is core of the machine)

  // execute the executor
  env.execute()

  println("WordCount4 end ...")
}
