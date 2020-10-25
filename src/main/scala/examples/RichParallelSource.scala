package examples

import  org.apache.flink.configuration.Configuration
import  org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction
import  org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext

class RichParallelSource extends RichParallelSourceFunction[Long]{

  var count = 1L
  var isRunning = true

  override def run(ctx: SourceContext[Long]) = {
    while (isRunning){
      ctx.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }

  override def cancel() = {
    isRunning = false
  }

  override def open(parameters: Configuration): Unit = super.open(parameters)

  override def close(): Unit = super.close()
}
