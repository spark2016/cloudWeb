package com.spark.main

import java.io.FileOutputStream

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by liuyifan on 16/6/1.
  */
object SparkMain {
  def main(args: Array[String]) : Unit = {
    if (args.length == 0) {
      println("No params.");
      sys.exit(-1);
    }
    val userId = args(0);
    val conf = new SparkConf().setAppName("DFGHJKL");
    val sc = new SparkContext(conf);

    var result : String = "";
    result += "{\"id\": \"" + userId + "\", ";
    result += "\"baseInfo\": \"" + profile(userId, sc) + "\", ";
    result += "\"profession\": \"" + profession(userId, sc) + "\", ";
    result += "\"interests\": \"" + interests(userId, sc) + "\", ";
    result += "\"pagerank\": \"" + pagerank(userId, sc) + "\"}\n";
//    result += "\"baseInfo\": \"" + profile(userId) + "\", ";
//    result += "\"profession\": \"" + profession(userId) + "\", ";
//    result += "\"interests\": \"" + interests(userId) + "\", ";
//    result += "\"pagerank\": \"" + pagerank(userId) + "\"}\n";

    println(result);

    val path = "/home/spark/project/result.dat"
//    val path = "/Users/liuyifan/Documents/result.dat";

    val fos = new FileOutputStream(path);
    fos.write(result.getBytes);
    fos.close();

    println("Succeed!");
    sc.stop()
  }

  def profile(id: String, sc: SparkContext) : String = {
//  def profile(id: String) : String = {
    // do something

    var profile = "";
    val name = "Jack";
    val sex = "m";
    val age = "30";
    profile += "name: " + name + "<br/>";
    profile += "sex: " + sex + "<br/>";
    profile += "age: " + age;
    profile;
  }

  def profession(id: String, sc: SparkContext) : String = {
//  def profession(id: String) : String = {
    // do something
    var profession = "doctor";
    profession;
  }

  def interests(id: String, sc: SparkContext) : String = {
//  def interests(id: String) : String = {
    // do something
    val interests = "hiking,something...";
    interests;
  }

  def pagerank(id: String, sc: SparkContext) : String = {
//  def pagerank(id: String) : String = {
    //do something
    val pagerank = "10.1";
    pagerank
  }
}
