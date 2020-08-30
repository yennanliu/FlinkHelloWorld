// package org.dev 

// import org.apache.flink.api.scala._
// import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
// import org.apache.flink.table.api.scala._

// /***
// modify from 
// https://gousios.org/courses/bigdata/2017/assignment-streaming-solutions.pdf
// ***/

// object LoadDataWithJson4s {

//   def main(args: Array[String]): Unit = {

//     // set up execution environment
//     val env = StreamExecutionEnvironment.getExecutionEnvironment
//     val tEnv = StreamTableEnvironment.create(env)

//     def strToEvent(in : String) : Event = {
//         val json = parse(in); //parse to json
//         val event : Event = json.extract[Event]; //extract to case class
//         return event;
//         }


//     //set default formats e.g. dates
//     implicit val formats = DefaultFormats
//     //case classes used to parse, type with backticks because type is a reserved keyword
//     //payload is still set to a parsed type, since that one differs per event (so we can parse it
//     after a filter)
//     class Event(id: String, ‘type‘: String, actor: Actor, repo: Repo, payload: JObject,
//     created_at : Date, public : Boolean);
    
//     class Actor(id: Integer, login: String, gravatar_id: String, url: String, avatar_url:
//     String);
    
//     class Repo(id: Integer, name: String, url: String);


//     val env = StreamExecutionEnvironment.getExecutionEnvironment
//     val tEnv = StreamTableEnvironment.create(env)

//     // read file
//     val file:DataStream[String] = env.readTextFile("data/sample.json");
//     //parse all the lines
//     val streamer = file.map(strToEvent(_));
//     //start the environment
//     env.execute();


//    }
// }