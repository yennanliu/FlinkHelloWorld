package org.example

import org.apache.flink.api.scala._
import scala.io.Source

object TestRun extends App {
  println("this is TestRun...")

  for (i <- 1 to 20)
    println(i)

}
