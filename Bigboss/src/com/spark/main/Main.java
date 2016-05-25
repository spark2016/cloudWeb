package com.spark.main;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import net.sf.json.JSONObject;

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

//        SparkConf conf = new SparkConf();
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> base = sc.textFile("/");


        JSONObject result = new JSONObject();

        // basic information
        JSONObject basicObj = new JSONObject();
        String name = "Xiao Ming";
        String sex = "f";
        String age = "20";
        basicObj.put("name", name);
        basicObj.put("sex", sex);
        basicObj.put("age", age);

        // professional information
        JSONObject profObj = new JSONObject();
        String job = "hunter";
        String habit = "eat";
        profObj.put("job", job);
        profObj.put("habit", habit);

        // interest
        JSONObject interestObj = new JSONObject();
        String interests = "music,hiking...";
        interestObj.put("interests", interests);

        // social network information
        JSONObject socialObj = new JSONObject();
        String pageRank = "1.1";
        String group = "boss";
        socialObj.put("pageRank", pageRank);
        socialObj.put("group", group);

        result.put("id", id);
        result.put("basic", basicObj);
        result.put("professional", profObj);
        result.put("interest", interestObj);
        result.put("social", socialObj);

        String res = result.toString();
        System.out.println(res);
    }
}
