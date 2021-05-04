package dev

/**
 *  Flink job that process event from socket (localhost:9999)
 *  and print the result in console
 *
 * // plz run the socket :
 * // nc -lk 9999
 */

// https://ci.apache.org/projects/flink/flink-docs-release-1.13/docs/dev/datastream/overview/
// https://www.codenong.com/cs105166917/

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object StreamFromSocketV1 extends App {

  val env = StreamExecutionEnvironment.getExecutionEnvironment
  // read from socket (port = 9999)
  val text = env.socketTextStream("localhost", 9999)

  val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
    .map { (_, 1) }
    .keyBy(_._1)
    .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
    .sum(1)

  // print it in console
  counts.print()

  // run it
  env.execute(this.getClass.getName)
}
