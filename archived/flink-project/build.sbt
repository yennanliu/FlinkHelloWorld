ThisBuild / resolvers ++= Seq(
    "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
    Resolver.mavenLocal
)

name := "Flink Project"

version := "FlHelloWorld"

organization := "org.example"

ThisBuild / scalaVersion := "2.11.12"

val flinkVersion = "1.10.0"

val flinkDependencies = Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-table-api-scala" % flinkVersion,
  "org.apache.flink" %% "flink-table-api-scala-bridge" % "1.9.0", 
  "org.apache.flink" %% "flink-parquet" % "1.9.2", 
  "org.apache.flink" % "flink-table" % "1.10.0" % "provided",


  // json4s 
  // https://mvnrepository.com/artifact/org.json4s/json4s-native
  "org.json4s" %% "json4s-native" % "3.7.0-M2"

)

lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies
  )

assembly / mainClass := Some("org.example.Job")

// make run command include the provided dependencies
Compile / run  := Defaults.runTask(Compile / fullClasspath,
                                   Compile / run / mainClass,
                                   Compile / run / runner
                                  ).evaluated

// stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
Compile / run / fork := true
Global / cancelable := true

// exclude Scala library from assembly
assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)
