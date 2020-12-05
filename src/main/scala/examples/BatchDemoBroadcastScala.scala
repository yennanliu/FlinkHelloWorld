package examples

import com.sun.tools.javac.util.ListBuffer
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

import scala.collection.mutable.ListBuffer

object BatchDemoBroadcastScala extends App{

  val env = ExecutionEnvironment.getExecutionEnvironment

  import org.apache.flink.api.scala._

  // get data need to broadcast
  val broadData = ListBuffer[Tuple2[String, Int]]()
  broadData.append(("zs", 199))
  broadData.append(("ls", 24))
  broadData.append(("xx", 77))

  // process the to-broadcast data
  val tupleData = env.fromCollection(broadData)
  val toBroadcastData = tupleData.map(tup => {
    Map(tup._1 -> tup._2)
  })

  val text = env.fromElements("zs", "ls", "ww")

  // TBC

}
