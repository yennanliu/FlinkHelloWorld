package examples

import java.util

//import org.apache.flink.streaming.api.collector.selector.OutputSelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

// UDF
import examples.ParallelSource

object StreamDemoMyPartitioner extends App {

  val env = StreamExecutionEnvironment.getExecutionEnvironment
  env.setParallelism(2)

  // implicit transformation

  import org.apache.flink.api.scala._

  val text = env.addSource(new ParallelSource)

  // transform Long to Tuple dtype
  val tupleData = text.map(line => {
    Tuple1(line)
  })

  val partitionData = tupleData.partitionCustom(new MyPartitioner, 0)

  val result = partitionData.map(line => {
    println("CURRENT PROCESS ID : " + Thread.currentThread().getId + " , value : " + line)
    line._1
  })

  result.print().setParallelism(1)
  env.execute("StreamDemoMyPartitioner")
}
