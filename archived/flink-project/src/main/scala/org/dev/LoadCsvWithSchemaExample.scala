// package org.dev 

// import org.apache.flink.quickstart.IncomingDataUnit
// import org.apache.flink.streaming.api.TimeCharacteristic
// import org.apache.flink.streaming.api.scala._
// import org.json4s.jackson.JsonMethods._
// import org.json4s.{DefaultFormats, _}


// object LoadCsvWithSchemaExample {

//   def main(args: Array[String]) {

//     val env = StreamExecutionEnvironment.createLocalEnvironment(4)

//     // [NS(Blog)]: We are following the simplest notion of Processing time, indicating that
//     // when an event enters this application is the point in time we assume that the event has occurred.
//     // In effect, we are ignoring the pubnub-generated TimeStamp.

//     env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
//     env.setParallelism(1)

//     val readings =
//       readIncomingReadings(env, "data/sample.json")
//       .map(e => (e.sensorUUID,e.ambientTemperature))
//       .countWindowAll(4,1)
//       .maxBy(1)


//     readings.print

//     env.execute("Scala IOT Stream  experiment Example")
//   }

//   // [NS (Blog)]: It is handy to convert the incoming JSON-formatted data into an object of itself.

//   private def readIncomingReadings(env: StreamExecutionEnvironment,inputPath: String) : DataStream[IncomingDataUnit] = {

//     def unjasonify(jsonString: String): IncomingDataUnit = {

//       implicit val formats = DefaultFormats

//       parse(jsonString)
//         .transformField {
//         case ("timestamp",v)            => ("timeStamp",v)
//         case ("ambient_temperature",v)  => ("ambientTemperature",v)
//         case ("photosensor",v)          => ("photoSensor",v)
//         case ("radiation_level",v)      => ("radiationLevel",v)
//         case ("sensor_uuid",v)          => ("sensorUUID",v)
//       }.extract[IncomingDataUnit]
//     }

//     env.readTextFile(inputPath).map(datum => {
//       val fields = datum.split(",")
//       IncomingDataUnit(
//         fields(0),              // sensorUUID
//         fields(1).toInt,        // radiationLevel
//         fields(2).toFloat,      // photoSensor
//         fields(3).toFloat,      // humidity
//         fields(4).toLong,       // timeStamp
//         fields(5).toFloat       // ambientTemperature
//       )
//     })
//   }

//   /**
//    * .assignTimestamps(new TimestampExtractor[IncomingDataUnit] {
//             def extractTimestamp (incoming: IncomingDataUnit, currentTimeStamp: Long): Long = {
//                   incoming.timeStamp
//             }
//             def extractWatermark(incoming: IncomingDataUnit, currentTimeStamp: Long): Long = incoming.timeStamp - 500
//             override def getCurrentWatermark: Long = Long.MinValue
//         })
//    *
//    */


// }