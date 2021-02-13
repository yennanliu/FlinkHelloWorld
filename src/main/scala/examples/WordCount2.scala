package examples

// https://github.com/apache/flink/blob/master/flink-examples/flink-examples-streaming/src/main/scala/org/apache/flink/streaming/scala/examples/wordcount/WordCount.scala

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
//import org.apache.flink.streaming.examples.wordcount.util.WordCountData

object WordCount2 extends App {

  // check input param
  val params = ParameterTool.fromArgs(args)

  // set up env
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  // make param available
  env.getConfig.setGlobalJobParameters(params)

  // get text data
  // read the text file from given input path
  val text =
    if (params.has("input")) {
      env.readTextFile(params.get("input"))
    } else {
      println("Executing WordCount example with default inputs data set.")
      println("Use --input to specify file input.")
      // get default test text data
      env.fromElements("data/test.text")
    }

  val counts: DataStream[(String, Int)] = text
    // split up the lines in pairs (2-tuples) containing: (word,1)
    .flatMap(_.toLowerCase.split("\\W+"))
    .filter(_.nonEmpty)
    .map((_, 1))
    // group by the tuple field "0" and sum up tuple field "1"
    .keyBy(_._1)
    .sum(1)

  // send the result
  if (params.has("output")){
    counts.writeAsText(params.get("output"))
  } else {
    println("Printing result to stdout. Use --output to specify output path.")
    counts.print()
  }

  // run the program
  env.execute("Streaming WordCount With Param")
}
