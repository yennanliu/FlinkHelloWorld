package streamSocketToHDFS

/**
 *  Flink job that process event from socket (localhost:9999)
 *  and save the result to HDFS
 *
 * // plz run the socket :
 * // nc -lk 9999
 */

// https://github.com/yennanliu/flinkhelloworld/blob/master/src/main/scala/dev/StreamFromSocketToHDFSV1.scala
// https://ci.apache.org/projects/flink/flink-docs-release-1.2/dev/connectors/filesystem_sink.html

import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

// stream sink
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink

object streamSocketToHDFSV1 extends App {

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

  val outputPath = "StreamFromSocketToHDFSV1"

  // build a flink sink
  // https://ci.apache.org/projects/flink/flink-docs-release-1.13/docs/connectors/datastream/streamfile_sink/

  val defaultRollingPolicy = DefaultRollingPolicy.builder()
    .withRolloverInterval(TimeUnit.MINUTES.toMillis(15))
    .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
    .withMaxPartSize(1024 * 1024 * 1024)
    .build[String,String]

  val sink: StreamingFileSink[String] = StreamingFileSink
    .forRowFormat(new Path(outputPath), new SimpleStringEncoder[String]("UTF-8"))
    .withRollingPolicy(defaultRollingPolicy)
    .build()

  text.addSink(sink)

  // run it
  env.execute(this.getClass.getName)
}
