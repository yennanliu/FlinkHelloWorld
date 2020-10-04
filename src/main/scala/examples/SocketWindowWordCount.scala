package examples

import org.apache.flink.api.scala._
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

object SocketWindowWordCount extends App {
  println("SocketWindowWordCount run ...")

  // set up port
  val port: Int = try {
    ParameterTool.fromArgs(args).getInt("port")
  } catch {
    case e: Exception => {
      System.err.println("No port set. will use default port : 9000--scala")
    }
      9000
  }

  // get env when running
  val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

  // connect to socket for getting data
  val text = env.socketTextStream("localhost", port, '\n')

  import org.apache.flink.api.scala._

  val windowCounts = text.flatMap(line => line.split("\\s"))
    .map(w => WordWithCount(w, 1))
    .keyBy("word")
    .timeWindow(Time.seconds(2), Time.seconds(1))
    .sum("count")
  //.reduce(a,b => WordWithCount(a.word, a.count+n.count))

  windowCounts.print().setParallelism(1)

  // run the process
  env.execute("Socket window count")

  case class WordWithCount(word:String, count:Long)
}
