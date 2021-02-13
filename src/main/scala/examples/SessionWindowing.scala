package examples

// https://github.com/apache/flink/blob/master/flink-examples/flink-examples-streaming/src/main/scala/org/apache/flink/streaming/scala/examples/windowing/SessionWindowing.scala


import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows
// TODO : fix below import
//import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time


object SessionWindowing extends App {
  val params = ParameterTool.fromArgs(args)
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  env.getConfig.setGlobalJobParameters(params)
  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
  env.setParallelism(1)

  val fileOutput = params.has("output")

  val input = List(
    ("a", 1L, 1),
    ("b", 1L, 1),
    ("b", 3L, 1),
    ("b", 5L, 1),
    ("c", 6L, 1),
    // We expect to detect the session "a" earlier than this point (the old
    // functionality can only detect here when the next starts)
    ("a", 10L, 1),
    // We expect to detect session "b" and "c" at this point as well
    ("c", 11L, 1)
  )


  val source: DataStream[(String, Long, Int)] = env.addSource(
    new SourceFunction[(String, Long, Int)]() {

      override def run(ctx: SourceContext[(String, Long, Int)]): Unit = {
        input.foreach(value => {
          ctx.collectWithTimestamp(value, value._2)
          ctx.emitWatermark(new Watermark(value._2 - 1))
        })
        ctx.emitWatermark(new Watermark(Long.MaxValue))
      }

      override def cancel(): Unit = {}

    })

  // here we create the session for each id with max timeout of 3 time units
  val aggregated: DataStream[(String, Long, Int)] = source
    .keyBy(_._1)
    // TODO : fix this when fix EventTimeSessionWindows import
    //.window(EventTimeSessionWindows.withGap(Time.milliseconds(3L)))
    // as the workaround here
    .window(SlidingProcessingTimeWindows.of(Time.milliseconds(2500), Time.milliseconds((500))))
    .sum(2)

  if (fileOutput){
    aggregated.writeAsText(params.get("output"))
  } else {
    print("Printing result to stdout. Use --output to specify output path.")
    println("*****")
    aggregated.print()
    println("*****")
  }

  // execute
  env.execute()
}
