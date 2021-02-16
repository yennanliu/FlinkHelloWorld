name := "flinkhelloworld"

version := "1.0"
scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  // flink core
  "org.apache.flink" %% "flink-scala" % "1.0.0",
  "org.apache.flink" %% "flink-clients" % "1.0.0",
  "org.apache.flink" %% "flink-streaming-scala" % "1.0.0",
  "org.apache.flink" % "flink-streaming-connectors" % "1.0.0",
  "org.apache.flink" %% "flink-connector-redis" % "1.1.0",

  // flink table
  "org.apache.flink" %% "flink-table" % "1.0.0" % "provided",
  //"org.apache.flink" %% "flink-table-api-scala" % "1.10.1",

  // batch process
  "org.apache.flink" %% "flink-scala" % "1.0.0",

  // stream process
  "org.apache.flink" %% "flink-streaming-scala" % "1.0.0" % "provided"

  // twitter
  //"org.apache.flink" %% "flink-connector-twitter" % "1.10.1"
)
    