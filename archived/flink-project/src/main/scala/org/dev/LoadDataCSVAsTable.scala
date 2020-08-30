package org.dev 

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.scala._
import org.apache.flink.types.Row
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.table.sources.CsvTableSource
import org.apache.flink.api.common.typeinfo.Types

/***

https://stackoverflow.com/questions/48823349/reading-csv-file-by-flink-scala-addsource-and-readcsvfile
https://ci.apache.org/projects/flink/flink-docs-release-1.10/dev/table/tableApi.html

***/

object LoadDataCSVAsTable {

  def main(args: Array[String]): Unit = {

    // set up execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    
    val tenv = StreamTableEnvironment.create(env)

    val csvtable = CsvTableSource
          .builder
          .path("FlinkHelloWorld/flink-project/data/sidewalk2.csv")
          .ignoreFirstLine
          .fieldDelimiter("|")
          .field("index", Types.STRING)
          .field("COUNTY_NA", Types.STRING)
          .field("VILL_NAME", Types.STRING)
          .field("PSTART", Types.STRING)
          .field("PEND", Types.STRING)
          .field("Shape_STLe", Types.FLOAT)
          .build

    tenv.registerTableSource("test", csvtable)

    val orders = tenv.from("test")

    val result = orders
           .where("Shape_STLe > 100")
           .where("PSTART != PEND")
           .groupBy('COUNTY_NA, 'VILL_NAME)
           .select('COUNTY_NA,'VILL_NAME, 'PSTART.count as 'cnt)
           .toDataSet[Row] 
           .print()

   }
}