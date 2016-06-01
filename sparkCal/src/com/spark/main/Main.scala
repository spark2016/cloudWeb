package com.spark.main

import java.io._
import java.util.Date
import scala.io.Source
import com.wanghuanming.tfidf._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.lib.LabelPropagation

object Main {
  def main(args: Array[String]) {
    //val userId = args(0)
    //设置运行环境
    val conf = new SparkConf().setAppName("Interest")
    conf.setMaster("local")
    var sc = new SparkContext(conf)
    val userId = "1628951200"
    val baseInfo = getBasic(userId,sc)
    val interest = getInterest(userId,sc)
    val socialInfo = getSocial(userId,sc)
    println(interest)
    println("social:" + socialInfo)    
  }
  def getInterest(userId: String,sc: SparkContext)= {
    val articles: RDD[String] = sc.textFile("hdfs://localhost:9001/weibo.txt")
    
    val articles_filter =  articles.filter(a => (a.split('\t').length > 2 && a.split('\t')(1) == userId));
    var res = ""
    TFIDF.getKeywords(articles_filter.collect().mkString, 5).foreach(e=>res=res+"<br/>"+e._1)
    res
  }
  def getBasic(userId: String,sc: SparkContext)= {
       val articles: RDD[String] = sc.textFile("hdfs://localhost:9001/userprofile.txt")
       val articles_filter =  articles.filter(a => (a.split('\t').length > 2 && a.split('\t')(0) == userId));
       val res = articles_filter.collect().mkString
       var basicInfo = ""
       basicInfo =  "<br/>" + res.split('\t')(1) + "<br/>" + res.split('\t')(2) + "<br/>"+res.split('\t')(3) + "<br/>"+ (if (res.split('\t')(6) == "f") '男' else '女')
       var professInfo = ""
       TFIDF.getKeywords(res.split('\t')(12), 3).foreach(e=>professInfo=professInfo+"<br/>"+e._1)
       // 11387	左琼	广东	广州	2009-08-27 16:00:00	2012-04-05 09:38:16	f	1	367	1858	6332	http://blog.sina.com.cn/zq4387	用你的毛，织我的脖！
       println("this is the info of basic " + basicInfo)
       println("this is the info of profess " + professInfo)
       basicInfo
  }
  def getSocial(userId: String,sc: SparkContext) = {
    val relationFile = "hdfs://localhost:9001/relation.txt"
    //load graph
    val graph = GraphLoader.edgeListFile(sc, relationFile)
    //Calculate page rank and save the result to VertexRDD
    val ranks = graph.pageRank(0.1).vertices
    val result = ranks.filter(text=>(text.toString.contains(userId)))
    val group = LabelPropagation.run(graph, 2)
    println(group)
    result.first()
  }

}