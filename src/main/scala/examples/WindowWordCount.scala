package examples

// https://github.com/apache/flink/blob/master/flink-examples/flink-examples-streaming/src/main/scala/org/apache/flink/streaming/scala/examples/windowing/WindowWordCount.scala

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

import examples.util.WordCountData

object WindowWordCount extends App {

  val params = ParameterTool.fromArgs(args)

  val env = StreamExecutionEnvironment.getExecutionEnvironment

  // get input data
  val text =
    if (params.has("input")) {
      // read the text file from path from input param
      env.readTextFile(params.get("input"))
    } else {
      println("Executing WindowWordCount example with default input data set.")
      println("Use --input to specify file input.")
      // get default test text data
      env.fromElements(WordCountData.WORDS: _*)
    }

  // make parameters available in the web interface
  env.getConfig.setGlobalJobParameters(params)

  val windowSize = params.getInt("window", 250)
  val slideSize = params.getInt("slide", 150)

  val counts: DataStream[(String, Int)] = text
    // split the lines in pairs (2-tuple) containing: (word,1)
    .flatMap(_.toLowerCase.split("\\W+"))
    .filter(_.nonEmpty)
    .map((_, 1))
    .keyBy(_._1)
    // create windows of windowSize records slided every slideSize records
    .countWindow(windowSize, slideSize)
    // group by the tuple field "0" and sum up tuple field "1"
    .sum(1)

  // emit the result
  if (params.has("output")) {
    counts.writeAsText(params.get("output"))
  } else {
    println("Printing result to stdout. Use --output to specify output path.")
    println("********")
    counts.print()
    println("**** counts = " + counts.print().toString)
    println("********")
  }

  // execute the program
  env.execute()
}
