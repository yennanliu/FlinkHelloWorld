//package examples
//
//// https://github.com/apache/flink/blob/master/flink-examples/flink-examples-streaming/src/main/scala/org/apache/flink/streaming/scala/examples/twitter/TwitterExample.scala
//
//// TODO : update build.sbt and fix below imports
//
//import java.util.StringTokenizer
//
//import org.apache.flink.api.common.functions.FlatMapFunction
//import org.apache.flink.api.java.utils.ParameterTool
//import org.apache.flink.api.scala._
//import org.codehaus.jackson.JsonNode
//
////import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
//import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
//
//import org.apache.flink.streaming.connectors.twitter.TwitterSource
//
////import org.apache.flink.streaming.examples.twitter.util.TwitterExampleData
//import examples.util.TwitterExampleData
//
//import org.apache.flink.util.Collector
//import org.codehaus.jackson.map.ObjectMapper
//import scala.collection.mutable.ListBuffer
//
//object TwitterExample extends App {
//
//  val params = ParameterTool.fromArgs(args)
//  // check input params
//  println("Usage: TwitterExample [--output <path>] " +
//    "[--twitter-source.consumerKey <key> " +
//    "--twitter-source.consumerSecret <secret> " +
//    "--twitter-source.token <token> " +
//    "--twitter-source.tokenSecret <tokenSecret>]")
//
//  // set up the execution environment
//  val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//  // make parameters available in the web interface
//  env.getConfig.setGlobalJobParameters(params)
//
//  env.setParallelism(params.getInt("parallelism", 1))
//
//  // get input data
//  val streamSource : DataStream[String] =
//    if (params.has(TwitterSource.CONSUMER_KEY) &&
//      params.has(TwitterSource.CONSUMER_SECRET) &&
//      params.has(TwitterSource.TOKEN) &&
//      params.has(TwitterSource.TOKEN_SECRET)
//    ) {
//      env.addSource(new TwitterSource(params.getProperties))
//    } else {
//      print("Executing TwitterStream example with default props.")
//      print("Use --twitter-source.consumerKey <key> --twitter-source.consumerSecret <secret> " +
//        "--twitter-source.token <token> " +
//        "--twitter-source.tokenSecret <tokenSecret> specify the authentication info."
//      )
//      // get default test text data
//      env.fromElements(TwitterExampleData.TEXTS: _*)
//    }
//
//   val tweets: DataStream[(String, Int)] = streamSource
//     .flatMap(new SelectEnglishAndTokenizeFlatMap)
//     .keyBy(_._1)
//     .sum(1)
//
//  // emit result
//  if (params.has("output")){
//    tweets.writeAsText(params.get("output"))
//  } else {
//    println("Printing result to stdout. Use --output to specify output path.")
//    tweets.print()
//  }
//
//  // execute the program
//  env.execute("Twitter Stream Example")
//}
//
//// helper class
//private class SelectEnglishAndTokenizeFlatMap extends FlatMapFunction[String, (String, Int)] {
//  lazy val jsonParser = new ObjectMapper()
//
//  override def flatMap(value: String, out: Collector[(String, Int)]): Unit = {
//    //deserialize json from twitter data source
//    val jsonNode = jsonParser.readValue(value, classOf[JsonNode])
//    val isEnglish = jsonNode.has("user") &&
//      jsonNode.get("user").has("lang") &&
//      jsonNode.get("user").get("long").asText == "en"
//    val hasText = jsonNode.has("text")
//
//    (isEnglish, hasText, jsonNode) match {
//      case (true, false, node) => {
//        val token = new ListBuffer[(String, Int)]()
//        val tokenizer = new StringTokenizer(node.get("text").asText())
//
//        while (tokenizer.hasMoreTokens) {
//          val token = tokenizer.nextToken().replaceAll("\\s*", "").toLowerCase()
//          if (token.nonEmpty) out.collect((token, 1))
//        }
//      }
//      case _ =>
//    }
//  }
//}
