package com.spark.main;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

/**
 * Created by liuyifan on 16/5/30.
 */
public class ProfessionUtil {
    /**
     * 根据id,获取professional information
     * @param id
     * @return
     */
    public static String profession(String id) {
        StringBuffer prof = new StringBuffer();

        // do something in spark<TODO>
        String job = "hunter";
        String habit = "eat";
        prof.append("{\"job\": \"" + job + "\"");
        prof.append(", \"habit\": \"" + habit + "\"}");
        return prof.toString();
    }
}
