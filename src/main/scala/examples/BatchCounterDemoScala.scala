package examples

import org.apache.flink.api.common.accumulators.IntCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

object BatchCounterDemoScala extends App{

  val env = ExecutionEnvironment.getExecutionEnvironment

  import org.apache.flink.api.scala._

  //val data = env.fromElements("a", "b", "c", "d")
  val data = env.fromElements("a", "b", "c", "d", "a", "b", "c", "d", "a", "b", "c", "d")

  val res = data.map(new RichMapFunction[String, String] {

    // 1. define accumulator
    val numLines = new IntCounter

    override def open(parameters: Configuration): Unit = {
      super.open(parameters)

      // 2. register accumulator
      getRuntimeContext.addAccumulator("num-lines", this.numLines)
    }

    override def map(value: String):String = {
      this.numLines.add(1)
      value
    }

  }).setParallelism(4)

  res.writeAsText("data/BatchCounterDemoScala")

  val jobResult = env.execute("BatchCounterDemoScala")
}
