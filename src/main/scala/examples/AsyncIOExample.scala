//package examples
//
//// https://github.com/apache/flink/blob/master/flink-examples/flink-examples-streaming/src/main/scala/org/apache/flink/streaming/scala/examples/async/AsyncIOExample.scala
//
//
//import java.util.concurrent.TimeUnit
//
//import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction
//import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
//import org.apache.flink.streaming.api.scala._
//
//// TODO : fix below import (would need to update build.sbt)
////import org.apache.flink.streaming.api.scala.async.ResultFuture
////import org.apache.flink.streaming.api.functions.async.ResultFuture
//
//import scala.concurrent.{ExecutionContext, Future}
//
//object AsyncIOExample extends App {
//
//  val timeout = 10000L
//
//  val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//  val input = env.addSource(new SimpleSource())
//
//  val asyncMapped = AsyncDataStream.orderededWait(input, timeout, TimeUnit.MILLISECONDS, 10) {
//    (input, collector: ResultFuture[Int]) =>
//      Future {
//        collector.complete(Seq(input))
//      } (ExecutionContext.global)
//  }
//
//  asyncMapped.print()
//
//  // execute the program
//  env.execute("Async I/O job")
//
//
//  // let's define some class below
//  class SimpleSource extends ParallelSourceFunction[Int] {
//    var running = true
//    var counter = 0
//
//    override def run(ctx: SourceContext[Int]): Unit = {
//      while (running) {
//        ctx.getCheckpointLock.synchronized {
//          ctx.collect(counter)
//        }
//
//        counter += 1
//      }
//    }
//
//    override def cancel(): Unit = {
//      running = false
//    }
//  }
//
//}
