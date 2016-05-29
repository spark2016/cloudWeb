package com.spark.main;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import org.codehaus.groovy.runtime.StringBufferWriter;

//import net.sf.json.JSONObject;

/**
 * Created by liuyifan on 16/5/23.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No param.");
            System.exit(-1);
        }

        String id = args[0];

        StringBuffer result = new StringBuffer();

        result.append("{\"id\": \"" + id + "\"");
        result.append(", \"baseInfo\": " + basic(id));
        result.append(", \"profession\": " + profession(id));
        result.append(", \"interests\": " + interests(id));
        result.append(", \"social\": " + social(id) + "}");

        System.out.println(result.toString());
    }

    /**
     * 根据id,获取basic information
     * @param id
     * @return
     */
    private static String basic(String id) {
        StringBuffer basic = new StringBuffer();

//        SparkConf conf = new SparkConf();
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> base = sc.textFile("/");

        // do something in spark<TODO>
        String name = "Skye";
        String sex = "f";
        String age = "20";
        basic.append("{\"name\": \"" + name + "\"");
        basic.append(", \"sex\": \"" + sex + "\"");
        basic.append(", \"age\": \"" + age + "\"}");
        return basic.toString();
    }

    /**
     * 根据id,获取professional information
     * @param id
     * @return
     */
    private static String profession(String id) {
        StringBuffer prof = new StringBuffer();

        // do something in spark<TODO>
        String job = "hunter";
        String habit = "eat";
        prof.append("{\"job\": \"" + job + "\"");
        prof.append(", \"habit\": \"" + habit + "\"}");
        return prof.toString();
    }

    /**
     * 根据id,获取interest information
     * @param id
     * @return
     */
    private static String interests(String id) {
        // do something in spark<TODO>
        String interests = "hiking,something...";
        return "\"" + interests +"\"";
    }

    /**
     * 根据id,获取social network information
     * @param id
     * @return
     */
    private static String social(String id) {
        StringBuffer social = new StringBuffer();

        // do something in spark<TODO>
        String pageRank = "1.1";
        String group = "boss";
        social.append("{\"pagerank\": \"" + pageRank + "\"");
        social.append(", \"group\": \"" + group + "\"}");
        return social.toString();
    }
}
