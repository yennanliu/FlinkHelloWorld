package examples

/**
 *  Word count in Stream way
 *
 *  plz run below command before running this script
 *  nc -lk 7777
 */

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

// https://www.youtube.com/watch?v=aF48pIkc7fw&list=PLmOn9nNkQxJGLnTsoWaHfvXrfpWiihoxV&index=8

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

  wordCountDataStream.print()

  // execute the executor
  env.execute()

  println("WordCount4 end ...")
}
