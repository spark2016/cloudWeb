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

        JSONObject result = new JSONObject();

        result.put("id", id);
        result.put("basic", basic(id));
        result.put("professional", profession(id));
        result.put("interest", interest(id));
        result.put("social", network(id));

        String res = result.toString();
        System.out.println(res);
    }

    /**
     * 根据id,获取basic information<TODO>
     * @param id
     * @return
     */
    private static JSONObject basic(String id) {
        JSONObject basicObj = new JSONObject();

//        SparkConf conf = new SparkConf();
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> base = sc.textFile("/");

        String name = "Xiao Ming";
        String sex = "f";
        String age = "20";
        basicObj.put("name", name);
        basicObj.put("sex", sex);
        basicObj.put("age", age);
        return basicObj;
    }

    /**
     * 根据id,获取professional information<TODO>
     * @param id
     * @return
     */
    private static JSONObject profession(String id) {
        JSONObject profObj = new JSONObject();
        String job = "hunter";
        String habit = "eat";
        profObj.put("job", job);
        profObj.put("habit", habit);
        return profObj;
    }

    /**
     * 根据id,获取interest information<TODO>
     * @param id
     * @return
     */
    private static JSONObject interest(String id) {
        JSONObject interestObj = new JSONObject();
        String interests = "music,hiking...";
        interestObj.put("interests", interests);
        return interestObj;
    }

    /**
     * 根据id,获取social network information<TODO>
     * @param id
     * @return
     */
    private static JSONObject network(String id) {
        JSONObject socialObj = new JSONObject();
        String pageRank = "1.1";
        String group = "boss";
        socialObj.put("pageRank", pageRank);
        socialObj.put("group", group);
        return socialObj;
    }
}
