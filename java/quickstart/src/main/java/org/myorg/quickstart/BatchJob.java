
// https://ci.apache.org/projects/flink/flink-docs-stable/dev/batch/examples.html#word-count
// https://www.freecodecamp.org/news/apache-flink-batch-example-in-java/

package org.myorg.quickstart;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.util.Collector;
import scala.Int;

public class BatchJob {

	public static void main(String[] args) throws Exception {

		// set up the batch execution environment
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		// java/quickstart/src/main/java/org/myorg/quickstart/BatchJob.java
		DataSet<String> dataSet =  env.readTextFile("../../../../../../../../data/test.txt");

		DataSet<Tuple2<String, Integer>> counts = dataSet
				.flatMap(new Tokenizer())
				// *** group by the tuple field "0" and sum up tuple field "1"
				.groupBy(0)
				.sum(1);

		counts.writeAsCsv("output", "\n", " ");

		// execute program
		env.execute("Flink Batch Java API Skeleton");
	}

	// User-defined functions
	public static class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>>{

		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
			// normalize and split the line
			String[] tokens = value.toLowerCase().split("\\w+");

			// emit the pairs
			for (String token : tokens){
				if (token.length() > 0){
					out.collect(new Tuple2<String, Integer>(token, 1));
				}
			}
		}
	}
}
