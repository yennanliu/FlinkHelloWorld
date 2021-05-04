//package examples
//
//import org.apache.flink.api.java.utils.ParameterTool
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.connectors.redis.RedisSink
//import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig
//import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}
//
//object StreamingDataToRedis {
//
//  def main(args: Array[String]) {
//    println("running StreamingDataToRedis ...")
//
//    val port = 9000
//
//    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//
//    val text = env.socketTextStream("localhost", port, '\n')
//
//    import org.apache.flink.api.scala._
//
//    val l_wordsData = text.map(line => ("l_words_scala", line))
//
//    val conf = new FlinkJedisPoolConfig.Builder().setHost("localhost").setPort(6379).build()
//
//    val redisSink = new RedisSink[Tuple2[String, String]](conf, new MyRedisMapper)
//
//    l_wordsData.addSink(redisSink)
//
//    // run the job
//    env.execute("Socket Window Count")
//  }
//
//  class MyRedisMapper extends RedisMapper[Tuple2[String, String]] {
//    override def getKeyFromData(data: (String, String)): String = {
//      data._1
//    }
//
//    override def getValueFromData(data: (String, String)): String = {
//      data._2
//    }
//
//    override def getCommandDescription: RedisCommandDescription = {
//      new RedisCommandDescription(RedisCommand.LPUSH)
//    }
//  }
//
//}