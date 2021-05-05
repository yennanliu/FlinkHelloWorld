package streamKafkaToHDFS

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}

// https://ci.apache.org/projects/flink/flink-docs-release-1.13/docs/connectors/datastream/kafka/

object streamKafkaToHDFSV1 extends App {

  // kafka config
  val properties = new Properties()
  properties.setProperty("bootstrap.servers", "localhost:9092")
  //properties.setProperty("group.id", "test") // “group.id” the id of the consumer group

  val KafkaTopic = "raw_data"

  val env = StreamExecutionEnvironment.getExecutionEnvironment

  val stream = env
    .addSource(
      new FlinkKafkaConsumer[String](KafkaTopic, new SimpleStringSchema(), properties)
        .setStartFromLatest()
    )

  stream.print()

  // save to HDFS

  env.execute("downstream-ins_jpw1a")
}
