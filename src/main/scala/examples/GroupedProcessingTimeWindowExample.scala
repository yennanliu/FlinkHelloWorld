package examples

// https://github.com/apache/flink/blob/master/flink-examples/flink-examples-streaming/src/main/scala/org/apache/flink/streaming/scala/examples/windowing/GroupedProcessingTimeWindowExample.scala

import org.apache.flink.api.java.operators.DataSource
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object GroupedProcessingTimeWindowExample extends App {

  // *** Main run code ***/

  val env = StreamExecutionEnvironment.getExecutionEnvironment
  env.setParallelism(4)

  val stream: DataStream[(Long, Long)] = env.addSource(new DataSource)

  stream
    .keyBy(_._1)
    .window(SlidingProcessingTimeWindows.of(Time.milliseconds(2500), Time.milliseconds((500))))
    .reduce((value1, value2) => (value1._1, value1._2 + value2._2))
    .addSink(new SinkFunction[(Long, Long)] {
      override def invoke(value: (Long, Long)): Unit = {
      }
    })

  // execute
  env.execute()

}

  // *** Define DataSource class **/
  /**
   * Parallel data source that serves a list of key-value pair.
   */
  private class DataSource extends RichParallelSourceFunction[(Long, Long)] {
    @volatile private var running = true

    override def run(ctx: SourceContext[(Long, Long)]): Unit = {
      val startTime = System.currentTimeMillis()

      val numElements = 20000000
      val numKeys = 10000
      var value = 1L
      var count = 0L

      while (running && count < numElements) {
        ctx.collect((value, 1L))

        count += 1
        value += 1

        if (value > numKeys) {
          value = 1L
        }
      }

      val endTime = System.currentTimeMillis()
      println(s"Took ${endTime - startTime}")
    }

    override def cancel(): Unit = running = false
}

