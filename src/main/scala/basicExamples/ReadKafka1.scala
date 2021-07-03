package basicExamples

// https://www.youtube.com/watch?v=-cbCbKbH6Xg&list=PLmOn9nNkQxJGLnTsoWaHfvXrfpWiihoxV&index=17
// https://github.com/yennanliu/KafkaSparkPoc/blob/main/kafka/src/main/scala/com/yen/Producer/producerV1.scala

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer


/**
 *    ReadKafka1
 *
 *    1) Read Stream from Kafka
 *    2) will read stream from below kafka consumer :
 *       -> https://github.com/yennanliu/KafkaSparkPoc/blob/main/kafka/src/main/scala/com/yen/Producer/producerV1.scala
 *       -> so plz run this kafka consumer first
 */

object ReadKafka1 extends App {

  println("ReadKafka1 start ...")

  val env = StreamExecutionEnvironment.getExecutionEnvironment

  val brokers = "127.0.0.1:9092"

  val topic = "raw_data"

  val props = new Properties()

  // key-value for kafka connect config below
  props.put("bootstrap.servers", brokers)
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("auto.offset.reset", "latest")

  /**
   * public FlinkKafkaConsumer(
   *   String topic, KafkaDeserializationSchema<T> deserializer, Properties props) {
   *   this(Collections.singletonList(topic), deserializer, props);
   *  }
   */
  // either define a source func
  // or can use the class that flink already implemented
  // needs 3 params : topic, deserializer, Properties
  val stream = env.addSource(
    // FlinkKafkaConsumer implements 1) checkpoint 2) deal with kafka offset -> will use last offset when flink program restart
    new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), props)
  )

  stream.print("stream").setParallelism(2)

  env.execute("ReadKafka1 run")

  println("ReadKafka1 end ...")
}
