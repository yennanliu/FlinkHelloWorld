//package YenExamples
//
//// https://ci.apache.org/projects/flink/flink-docs-release-1.0/apis/streaming/connectors/hdfs.html
//
//import org.apache.flink.api.java.utils.ParameterTool
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.streaming.connectors.fs.RollingSink
//
//import org.apache.flink.api.common.serialization.SimpleStringEncoder
//import org.apache.flink.core.fs.Path
//import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
//import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy
//
//
//object saveToHDFS extends App {
//
//  // check input param
//  val params = ParameterTool.fromArgs(args)
//
//  // set up env
//  val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//  // make param available
//  env.getConfig.setGlobalJobParameters(params)
//
//  // get text data
//  // read the text file from given input path
//  val text =
//  if (params.has("input")) {
//    env.readTextFile(params.get("input"))
//  } else {
//    println("Executing WordCount example with default inputs data set.")
//    println("Use --input to specify file input.")
//    // get default test text data
//    env.fromElements("data/test.text")
//  }
//
//  val counts: DataStream[(String, Int)] = text
//    // split up the lines in pairs (2-tuples) containing: (word,1)
//    .flatMap(_.toLowerCase.split("\\W+"))
//    .filter(_.nonEmpty)
//    .map((_, 1))
//    // group by the tuple field "0" and sum up tuple field "1"
//    .keyBy(_._1)
//    .sum(1)
//
//  // send the result
//  if (params.has("output")) {
//    counts.writeAsText(params.get("output"))
//  } else {
//    println("Printing result to stdout. Use --output to specify output path.")
//    counts.print()
//
//    /******************
//     * save to HDFS
//     ******************/
//
//    // define the sink
////    val sink: StreamingFileSink[String] = StreamingFileSink
////      .forRowFormat(new Path(outputPath), new SimpleStringEncoder[String]("UTF-8"))
////      .withRollingPolicy(
////        DefaultRollingPolicy.builder()
////          .withRolloverInterval(TimeUnit.MINUTES.toMillis(15))
////          .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
////          .withMaxPartSize(1024 * 1024 * 1024)
////          .build())
////      .build()
//
//    println("**** TO HDFS ****")
//    counts.addSink(new RollingSink[(String, Int)]("/Flinkoutput/saveToHDFS"))
//
//    counts.addSink(new RollingSink[(String, Int)]())
//    println("**** TO HDFS ****")
//  }
//
//  // run the program
//  env.execute("Streaming WordCount With Param")
//}