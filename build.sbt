name := "flinkhelloworld"

version := "1.0"
scalaVersion := "2.10.4"

libraryDependencies ++= Seq("org.apache.flink" %% "flink-scala" % "1.0.0",
  "org.apache.flink" %% "flink-clients" % "1.0.0",
  "org.apache.flink" %% "flink-streaming-scala" % "1.0.0",
  "org.apache.flink" % "flink-streaming-connectors" % "1.0.0",
  "org.apache.flink" %% "flink-connector-redis" % "1.1.0"
)
    