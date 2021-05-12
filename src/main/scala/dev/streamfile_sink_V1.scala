package dev

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import org.apache.flink.api.common.serialization.{SimpleStringEncoder, SimpleStringSchema}
import org.apache.flink.core.fs.Path
import org.apache.flink.core.io.SimpleVersionedSerializer
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.OnCheckpointRollingPolicy
import org.apache.flink.streaming.api.functions.sink.filesystem.{BucketAssigner, OutputFileConfig, StreamingFileSink}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

// https://ci.apache.org/projects/flink/flink-docs-release-1.13/docs/connectors/datastream/streamfile_sink/

object streamfile_sink_V1 extends App {

  // kafka config
  val properties = new Properties()
  properties.setProperty("bootstrap.servers", "localhost:9092")

  val KafkaTopic = "raw_data"

  val env = StreamExecutionEnvironment.getExecutionEnvironment

  val stream = env
    .addSource(
      new FlinkKafkaConsumer[String](KafkaTopic, new SimpleStringSchema(), properties)
        .setStartFromLatest()
    )

  val config = OutputFileConfig
    .builder()
    .withPartPrefix("prefix")
    .withPartSuffix(".ext")
    .build()

  class KeyBucketAssigner extends BucketAssigner[String, String] {

    // http://shzhangji.com/blog/2018/12/23/real-time-exactly-once-etl-with-apache-flink/
    override def getBucketId(element: String, context: BucketAssigner.Context): String = {
      val objectMapper = new ObjectMapper()
      val node = objectMapper.readTree(element)
      val date = node.path("timestamp").floatValue() * 1000
     val  partitionValue = new SimpleDateFormat("yyyyMMdd").format(new Date)
      "dt=" + partitionValue
    }

    override def getSerializer: SimpleVersionedSerializer[String] = SimpleVersionedStringSerializer.INSTANCE
  }

  val outputPath = "/someOutputPath"

  val sink = StreamingFileSink
    .forRowFormat(new Path(outputPath), new SimpleStringEncoder[String]("UTF-8"))
    .withBucketAssigner(new KeyBucketAssigner())
    .withRollingPolicy(OnCheckpointRollingPolicy.build())
    .withOutputFileConfig(config)
    .build()

  stream.addSink(sink)
}
