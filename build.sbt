name := "flinkhelloworld"

version := "1.0"

scalaVersion := "2.12.12"

val flinkVersion = "1.12.2"

lazy val versions = new {
  val flink = "1.12.0"
}

libraryDependencies ++= Seq(

    // flink core
    "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
    "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "provided",
    "org.apache.flink" %% "flink-clients" % flinkVersion,
    "org.apache.flink" % "flink-formats" % flinkVersion,
    "org.apache.flink" % "flink-compress" % flinkVersion,
    "org.apache.flink" %% "flink-connector-kafka" % flinkVersion,

    // hadoop
    "org.apache.hadoop" % "hadoop-common" % "2.4.0",
    "com.github.scopt" %% "scopt" % "3.7.0",
    "org.scalatest" %% s"scalatest" % "3.0.3" % "test",

    // joda
    "joda-time" % "joda-time" % "2.10.10",
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
