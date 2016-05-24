<%@ page language="java" contentType="text/html;charset=UTF-8"  import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>大数据分析</title>

    <meta name="HandheldFriendly" content="True" />
    <meta name="MobileOptimized" content="320" />
    <link rel="shortcut icon" href="http://expo.bootcss.com/assets/ico/favicon.png?v=6ea1eb82d8">

    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
    @keyframes change{
        0%{
            background: url("./images/pika.png");
            background-repeat: no-repeat;
            background-size: cover;
        }
        33%{
            background: url("./images/wukong.png");
            background-repeat: no-repeat;
            background-size: cover;
        }
        66%{
            background: url("./images/lufei.png");
            background-repeat: no-repeat;
            background-size: cover;
        }
        100%{
            background: url("./images/mingren.png");
            background-repeat: no-repeat;
            background-size: cover;
        }
    }
    .site-header {
        background-color: #6f5499;
        color: #ffffff;
    }
    .container{
        text-align: center;
    }
    .header{
        width: 100%;
        padding: 0 25%;
    }
    .content{
        position: relative;
        width: 100%;
        height: 500px;
    }
    .user_head_img{
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        margin: auto;
        width: 150px;
        height: 150px;
        background: url("./images/unknown.jpg");
        background-repeat: no-repeat;
        background-size: cover;
        border-radius: 10px;
    }
    .info{
        position: absolute;
        width: 25%;
        height: 200px;
        background: #f7f7f9;
        display: inline-block;
        border-radius: 10px;
        opacity: 0;
        transition: opacity 0.8s ease-in-out;
        -webkit-transition: opacity 0.8s ease-in-out;
    }
    .lefttop{
        opacity: 1;
        left: 10%;
        top: 5%;
    }
    .righttop{
        opacity: 1;
        top: 5%;
        right: 10%;
    }
    .leftbottom{
        opacity: 1;
        left: 10%;
        bottom: 5%;
    }
    .rightbottom{
        opacity: 1;
        right: 10%;
        bottom: 5%;
    }
    .title{
        margin: 5px auto;
        font-size: 20px;
        text-align: center;
    }
    .text_content{
        padding: 0 20%;
        font-size: 17px;
    }
    .reset{
        opacity: 0;
    }
    </style>

</head>
<body>
    <header class="site-header jumbotron">
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <h1>User Profile</h1>
                    <p>使用 <strong>scpapy</strong> 框架进行数据收集<br>
                        <span class="package-amount">使用 <strong>spark</strong> 进行数据分析</span>
                    </p>
                    <div class="row" style="margin: 0 20%;">
                        <div class="col-lg-12">
                            <div class="input-group">
	                             <input type="text" class="form-control" name="userId" id="userId"placeholder="Search for...">
	                             <span class="input-group-btn">
	                               <button id="go" class="btn btn-success dropdown-toggle" type="submit">Go!</button>
	                             </span> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <div class="content">
        <div class="user_head_img"></div>
        <div class="user_base info">
            <div class="title">基本信息</div>
            <div class="text_content"></div>
        </div>
        <div class="user_hobit info">
            <div class="title">兴趣爱好</div>
            <div class="text_content"></div>
        </div>
        <div class="user_profess info">
            <div class="title">用户职业</div>
            <div class="text_content"></div>
        </div>
        <div class="user_pagerank info">
            <div class="title">pagerank</div>
            <div class="text_content"></div>
        </div>
    </div>
    <%! //String baseInfo;%>
	<%
      String baseInfo=(String)request.getAttribute( "baseInfo");
	  //System.out.println("baseInfo is in the jsp" + baseInfo);
    %>
    <script src="./js/jquery-2.2.3.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script type="text/javascript">
    $("#go").click(function(){
        $(".info").each(function(){
            $(this).children(".text_content").empty();
            $(this).addClass("reset");
        });
        $(".user_head_img").css({
            "-webkit-animation":"change 1s linear infinite",
            "animation":"change 1s linear infinite"
        });
        
        //在这个地方发送ajax请求进行用户搜索
        var userId=document.getElementById("userId").value;
        var req;
        //创建一个XMLHttpRequest对象req
        if(window.XMLHttpRequest) {
            //IE7, Firefox, Opera支持
            req = new XMLHttpRequest();
        }else if(window.ActiveXObject) {
            //IE5,IE6支持
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }
        /*
         open(String method,String url, boolean )函数有3个参数
         method参数指定向servlet发送请求所使用的方法，有GET,POST等
         boolean值指定是否异步，true为使用，false为不使用。
         我们使用异步才能体会到Ajax强大的异步功能。
         */
        req.open("POST", "SparkServlet", true);
        req.setRequestHeader( "Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        //onreadystatechange属性存有处理服务器响应的函数,有5个取值分别代表不同状态
        req.onreadystatechange = callback;
        //send函数发送请求
        req.send("userId=" + userId);
        function callback() {
            if(req.readyState == 4 && req.status == 200) {
                var data = req.responseText;
                var text = JSON.parse(data);
                setTimeout(function(){//该函数写在当ajax返回成功的回调函数里
                    $(".user_head_img").css({"-webkit-animation":"none","animation":"none"});
                    $(".user_head_img").css({//设置返回的用户头像链接
                        "background":"url('./images/lufei.png') no-repeat",
                        "background-size":"cover"
                    });

                    //设置各个信息域的内容
                    $(".user_base .text_content").append("baseInfo"+'<br>'+text.baseInfo);
                    $(".user_hobit .text_content").append("interests"+'<br>'+text.interests);
                    $(".user_profess .text_content").append("profession"+'<br>'+text.profession);
                    $(".user_pagerank .text_content").append("pagerank"+'<br>'+text.pagerank);
                    //依次显示各个信息域
                    $(".user_base").removeClass("reset");
                    $(".user_base").addClass("lefttop");
                    setTimeout(function(){
                        $(".user_hobit").removeClass("reset");
                        $(".user_hobit").addClass("righttop");
                        setTimeout(function(){
                            $(".user_profess").removeClass("reset");
                            $(".user_profess").addClass("leftbottom");
                            setTimeout(function(){
                                $(".user_pagerank").removeClass("reset");
                                $(".user_pagerank").addClass("rightbottom");
                            },500);
                        },500);
                    },500);
                },1800);
            }
        }
    });
    </script>
    
</body>
</html>