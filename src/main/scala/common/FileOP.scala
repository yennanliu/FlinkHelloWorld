package common

import java.io.File
import scala.util.Try

class FileOP {
  def DeleteFileIfExist(f: File): Any = {
    Try {
      f.delete
      println(s"delete file : $f OK")
    }.getOrElse(println("file not exist, no need to delete"))
  }
}

class FileCheck(f: File) {
  // https://stackoverflow.com/questions/30015165/scala-delete-file-if-exist-the-scala-way/30015240
  def check = if (f.exists) Some(f) else None //returns "Maybe" monad
  def remove = if (f.delete()) Some(f) else None //returns "Maybe" monad
}
