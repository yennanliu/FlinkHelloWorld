package examples

import org.apache.flink.api.common.functions.Partitioner

class MyPartitioner extends Partitioner[Long]{

  override def partition(key: Long, numPartitions: Int) = {
    println("TOTAL PARTITION COUNTS : " + numPartitions)

    if (key % 2 == 0){
      0
    }
    else {
      1
    }
  }

}
