package examples

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

object BatchDemoDistributedCache extends App{

  println("BatchDemoDistributedCache run ...")

  val env = ExecutionEnvironment.getExecutionEnvironment

  import org.apache.flink.api.scala._

  // register the doc
  env.registerCachedFile("data/test.txt", "b.txt")

  val data = env.fromElements("a", "b", "c", "d")

  val result = data.map(new RichMapFunction[String, String] {

    override def open(parameters: Configuration): Unit = {

      super.open(parameters)

      // use the doc
      val myFile = getRuntimeContext.getDistributedCache.getFile("b.txt")

      val lines = FileUtils.readLines(myFile)

      val it = lines.iterator()

      while (it.hasNext){
        val line = it.next();
        println(s"line : " +line)
      }
    }
    override def map(value: String) = {
      value
    }
  })

  result.print()
}
