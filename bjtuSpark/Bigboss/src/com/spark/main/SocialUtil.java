package com.spark.main;

/**
 * Created by liuyifan on 16/5/30.
 */
public class SocialUtil {
    /**
     * 根据id,获取social network information
     * @param id
     * @return
     */
    public static String social(String id) {
        StringBuffer social = new StringBuffer();

        // do something in spark<TODO>
        String pageRank = "1.1";
        String group = "boss";
        social.append("{\"pagerank\": \"" + pageRank + "\"");
        social.append(", \"group\": \"" + group + "\"}");
        return social.toString();
    }
}
