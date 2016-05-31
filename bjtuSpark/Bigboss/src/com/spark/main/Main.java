package com.spark.main;

import java.io.File;
import java.io.FileOutputStream;

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
        result.append(", \"baseInfo\": " + BasicUtil.basic(id));
        result.append(", \"profession\": " + ProfessionUtil.profession(id));
        result.append(", \"interests\": " + InterestUtil.interests(id));
        result.append(", \"social\": " + SocialUtil.social(id) + "} \n");

        String res = result.toString();

        System.out.print(res);
        String path = "/home/spark/project/result.dat";
//        String path = "/Users/liuyifan/Documents/result.dat";

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(path));
            out.write(res.getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Succeed!");
    }
}
