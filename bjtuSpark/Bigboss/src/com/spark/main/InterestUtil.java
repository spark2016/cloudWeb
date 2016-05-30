package com.spark.main;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

/**
 * Created by liuyifan on 16/5/30.
 */
public class InterestUtil {
    /**
     * 根据id,获取interest information
     * @param id
     * @return
     */
    public static String interests(String id) {
        // do something in spark<TODO>
        String interests = "hiking,something...";
        return "\"" + interests +"\"";
    }
}
