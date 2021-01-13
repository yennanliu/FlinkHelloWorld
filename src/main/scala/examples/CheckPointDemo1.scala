package examples

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object CheckPointDemo1 extends App{

  println ("CheckPointDemo1 run...")

  val env = StreamExecutionEnvironment.getExecutionEnvironment

  env.enableCheckpointing(1000)

  env.getCheckpointConfig.getCheckpointingMode() //default value : CheckpointingMode.EXACTLY_ONCE

  env.getCheckpointConfig.setCheckpointInterval(500)

  //env.getCheckpointConfig.setMinPauseBetweenCheckPoints(500)

  env.getCheckpointConfig.setCheckpointTimeout(60000)

  env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)

  //env.getCheckpointConfig.enableExternalizedCheckpoints(EnableExternalizedCheckpoints.RETAIN_ON_CANCELLATION)
}
