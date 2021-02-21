name := "flinkhelloworld"

version := "1.0"
scalaVersion := "2.10.4"

lazy val versions = new {
  val flink = "1.12.0"
}

libraryDependencies ++= Seq(
  // flink core
  "org.apache.flink" %% "flink-scala" % versions.flink,
  "org.apache.flink" %% "flink-clients" % versions.flink,
  "org.apache.flink" %% "flink-streaming-scala" % versions.flink,
  "org.apache.flink" % "flink-streaming-connectors" % "1.0.0",
  "org.apache.flink" %% "flink-connector-redis" % "1.1.0"

  // flink table
  //"org.apache.flink" %% "flink-table" % versions.flink % "provided"
  //"org.apache.flink" %% "flink-table-api-scala" % "1.10.1",

  // twitter
  //"org.apache.flink" %% "flink-connector-twitter" % "1.10.1"
)
    