package streaming.examples

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows
import org.apache.flink.streaming.api.windowing.triggers.{CountTrigger, PurgingTrigger}
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow


object WindowAnatomy {
  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val source = env.socketTextStream("localhost",9000)

    val values = source.flatMap(value => value.split("\\s+")).map(value => (value,1))

    val keyValue = values.keyBy(0)

    // define the count window without purge

    val countWindowWithoutPurge = keyValue.window(GlobalWindows.create()).
      trigger(CountTrigger.of(2))


    val countWindowWithPurge = keyValue.window(GlobalWindows.create()).
      trigger(PurgingTrigger.of(CountTrigger.of[GlobalWindow](2)))

    countWindowWithoutPurge.sum(1).print()

    countWindowWithPurge.sum(1).print()

    env.execute()

  }
}
