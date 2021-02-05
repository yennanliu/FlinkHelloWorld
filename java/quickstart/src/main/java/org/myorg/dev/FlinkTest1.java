package org.myorg.dev;

// https://ci.apache.org/projects/flink/flink-docs-release-1.12/learn-flink/datastream_api.html
// Trouble shooting : Apache Flink WordCount Example - Exception in thread “main” java.lang.NoClassDefFoundError: org/apache/flink/api/common/functions/FlatMapFunction
// https://stackoverflow.com/questions/40073413/apache-flink-wordcount-example-exception-in-thread-main-java-lang-noclassdef

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.functions.FilterFunction;

public class FlinkTest1 {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Person> flintstones = env.fromElements(
                new Person("jj", 45),
                new Person("kk", 3432),
                new Person("aa", 77),
                new Person("young", 18)
        );

        DataStream<Person> adults = flintstones.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) throws Exception {
                return person.age >= 18;
            }
        });

        adults.print();

        env.execute();
    }

    public static class Person{
        public String name;
        public Integer age;
        public Person() {};

        public Person(String name, Integer age){
            this.name = name;
            this.age = age;
        }

        public String toString(){
            return this.name.toString() + ": age " + this.age.toString();
        }
    }
}
