package basicExamples

import java.io.File

import common.FileOP
import org.apache.flink.api.scala.ExecutionEnvironment

object BatchWordCount extends App {
  val inputPath = "data/test.txt"
  val output = "data/output/test_output.csv"

  val env = ExecutionEnvironment.getExecutionEnvironment
  val text = env.readTextFile(inputPath)

  // import implicit transformation

  import org.apache.flink.api.scala._

  val counts = text.flatMap(_.toLowerCase.split("\\W+"))
    .filter(_.nonEmpty)
    .map((_, 1))
    .groupBy(0)
    .sum(1)

  // if output exist, delete it
  val outputFile = new File(output)
  val file_op = new FileOP
  file_op.DeleteFileIfExist(outputFile)

  counts.writeAsCsv(output, "\n", " ").setParallelism(1)
  env.execute("BATCH WORD COUNT")
}
