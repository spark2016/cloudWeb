package com.spark.sparkDemo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

public class SparkMain {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        JavaSparkContext sc = new JavaSparkContext(conf);
        //read hdfs file
        JavaRDD<String> input = sc.textFile("/input/word.txt");
        //切分成单词
        JavaRDD<String> words = input.flatMap(new FlatMapFunction<String,String>() {
            public Iterable<String> call(String x) throws Exception {
                return Arrays.asList(x.split(" "));
            }
        });

        //转成键值对计数
        JavaPairRDD<String,Integer> counts = words.mapToPair(new PairFunction<String,String,Integer>(){
            public Tuple2<String, Integer> call(String x) throws Exception {
                return new Tuple2(x,1);
            }
        }).reduceByKey(new Function2<Integer,Integer,Integer>(){
            public Integer call(Integer x, Integer y) throws Exception {
                return x+y;
            }
        });

        //结果保存到hdfs中
        counts.saveAsTextFile("/output2");

    }
}

