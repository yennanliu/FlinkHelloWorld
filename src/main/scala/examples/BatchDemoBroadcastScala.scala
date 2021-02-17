package examples

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

import scala.collection.mutable.ListBuffer

object BatchDemoBroadcastScala extends App {

  val env = ExecutionEnvironment.getExecutionEnvironment

  import org.apache.flink.api.scala._

  // get data need to broadcast
  val broadData = ListBuffer[Tuple2[String, Int]]()
  broadData.append(("zs", 199))
  broadData.append(("ls", 24))
  broadData.append(("ww", 77))

  // process the to-broadcast data
  val tupleData = env.fromCollection(broadData)
  val toBroadcastData = tupleData.map(tup => {
    Map(tup._1 -> tup._2)
  })

  val text = env.fromElements("zs", "ls", "ww")

  val result = text.map(new RichMapFunction[String, String] {

    var listData: java.util.List[Map[String, Int]] = null
    var allMap = Map[String, Int]()

    override def open(parameters: Configuration): Unit = {
      super.open(parameters)

      // get broadcast data
      this.listData = getRuntimeContext.getBroadcastVariable[Map[String, Int]]("broadcastMapName")

      val it = listData.iterator()
      while (it.hasNext) {
        val next = it.next()
        allMap = allMap.++(next)
      }
    }

    override def map(value: String): String = {
      val age = allMap.get(value).get
      value + "," + age
    }
  }).withBroadcastSet(toBroadcastData, "broadcastMapName") // run the op for broadcast data

  result.print()
}
