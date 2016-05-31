package com.spark.main;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

/**
 * Created by liuyifan on 16/5/30.
 */
public class BasicUtil {
    /**
     * 根据id,获取basic information
     * @param id
     * @return
     */
    public static String basic(String id) {
        StringBuffer basic = new StringBuffer();

//        SparkConf conf = new SparkConf();
//        JavaSparkContext sc = new JavaSparkContext(conf);
//
//        sc.close();

        // do something in spark<TODO>
        String name = "Skye";
        String sex = "f";
        String age = "20";
        basic.append("\"name: " + name + "<br/>");
        basic.append(" sex: " + sex + "<br/>");
        basic.append(" age: " + age + "\"");
        return basic.toString();
    }
}
