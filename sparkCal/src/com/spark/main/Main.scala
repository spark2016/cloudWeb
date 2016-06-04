package com.spark.main

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.lib.LabelPropagation
import java.io._
import java.util.Date
import scala.io.Source
import com.wanghuanming.tfidf._
import org.apache.log4j.{Level, Logger}

object Main {
  def main(args: Array[String]) {
    // 1. local
//	  val userId = "1035748400";
    // 1. yarn
    //设置运行环境
	  if (args.length == 0) {
		  println("No params.");
		  sys.exit(-1);
	  }
	  val userId = args(0)
	  
	  
    val conf = new SparkConf().setAppName("sparkCal")
    // 2. local
//    conf.setMaster("local")
    var sc = new SparkContext(conf)
    val baseInfo = getBasic(userId,sc)
    val interest = getInterest(userId,sc)
    val socialInfo = getSocial(userId,sc)
    val professInfo = getProfess(userId,sc)

    var result : String = "";
    result += "{\"id\": \"" + userId + "\", ";
    result += "\"baseInfo\": \"" + baseInfo + "\", ";
    result += "\"profession\": \"" + professInfo + "\", ";
    result += "\"interests\": \"" + interest + "\", ";
    result += "\"pagerank\": \"" + socialInfo + "\"}\n";

    println(result)
    
    var tempFile: RDD[String] = sc.makeRDD(List(result))
    tempFile.repartition(1).saveAsTextFile("hdfs://spark1:9000/weibo/result.dat")

    println("Succeed!");
    sc.stop()
  }
  def getInterest(userId: String,sc: SparkContext)= {
    // 4. yarn
    val articles: RDD[String] = sc.textFile("hdfs://spark1:9000/weibo/weibo.txt")
    
    val articles_filter =  articles.filter(a => (a.split('\t').length > 2 && a.split('\t')(1) == userId));
    var res = ""
    TFIDF.getKeywords(articles_filter.collect().mkString, 5).foreach(e=>res=res+"<br/>"+e._1)
    println("this is the interst info: " + res)
    res
  }
  def getBasic(userId: String,sc: SparkContext)= {
      // 5. yarn
       val articles: RDD[String] = sc.textFile("hdfs://spark1:9000/weibo/userprofile.txt")
       val articles_filter =  articles.filter(a => (a.split('\t').length > 2 && a.split('\t')(0) == userId));
       val res = articles_filter.collect().mkString
       var basicInfo = ""
       basicInfo =  "<br/>" + res.split('\t')(1) + "<br/>" + res.split('\t')(2) + "<br/>"+res.split('\t')(3) + "<br/>"+ (if (res.split('\t')(6) == "f") '男' else '女')
       println("this is the info of basic " + basicInfo)
       basicInfo
  }
  def getSocial(userId: String,sc: SparkContext) = {
    // 6. yarn
    val relationFile = "hdfs://spark1:9000/weibo/relation1.txt"
    //load graph
    val graph = GraphLoader.edgeListFile(sc, relationFile)
    //Calculate page rank and save the result to the file
    /*    
     *  val ranks = graph.pageRank(0.1).vertices
     *  val result = ranks.filter(text=>(text.toString.contains(userId)))
     *  val allranks = ranks.collect().mkString("\n")
     *  println("this is all of the ranks:")
     *  val path = "/Users/baiyilin/Desktop/pagerank.dat"
     *  val fos = new FileOutputStream(path);
     *  fos.write(allranks.getBytes);
     *  fos.close();
     */

     //7. yarn
    val articles: RDD[String] = sc.textFile("hdfs://spark1:9000/pagerank.txt")
    val articles_filter =  articles.filter(a => a.split(',')(0) == userId)
    val res = articles_filter.collect().mkString
    var pagerank = ""
    var ret = ""
    pagerank =  res.split(',')(1)
    val groupGraph = LabelPropagation.run(graph, 3)
    val user = groupGraph.vertices.filter(_._1.toString().contains(userId))
    val label = user.first()._2
    val groups = groupGraph.vertices.groupBy(_._2)
    val members = groups.filter{case(key, _) => key == label}.values.flatMap(i => i.toList).collect()
    val community = members.map(a=>a._1)
    var result=""

    if(community.length > 5){
      for(i <- 1 to 5) {
        result += community(i)
        result += "<br/>"
      }
    }else{
      for(i <- 1 to community.length-1) {
        result =result +community(i)
        result += "<br/>"
      }
    }  
    ret = "pagerank: " + pagerank + "<br/>" + "label: "+ label + "<br/>" + "community members: "+ result
    ret
  }
  def getProfess(userId: String,sc: SparkContext)= {
    val zhihuFile: RDD[String] = sc.textFile("hdfs://spark1:9000/weibo/users.dat")
    val zhihuFile_has_weibo = zhihuFile.filter(a => (a.split('\t').length > 11 && a.split('\t')(11) != "无"))
    // println (zhihuFile_has_weibo.collect().mkString)
    val weibo_url =  zhihuFile_has_weibo.filter(a => (a.split('\t')(11).split("/").length == 5 && a.split('\t')(11).split("/")(4) == userId))
    val res = weibo_url.collect().mkString
    println(res)
    var professInfo = ""
    professInfo =  "<br/>" + res.split('\t')(7) +"<br/>" + res.split('\t')(8)+"<br/>" + res.split('\t')(9)
    println("this is the info of profess" + professInfo)
    professInfo
  }
}