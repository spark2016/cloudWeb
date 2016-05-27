package core.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import core.model.SparkService;
 
@Controller
public class SparkController {
	
	@Autowired
	SparkService service;
	
    /**
     * @user lyahb
     * 2014年9月4日
     */
    @RequestMapping(value = "/SparkServlet", method = RequestMethod.POST)
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //对应的是WebContent目录下的WEB-INF目录下的jsp目录下的demo下的index.jsp
        //请查看配置文件springMvc3-servlet.xml仔细体会一下
    	String userId = request.getParameter("userId");
        //TODO
		String userInfo = service.getSparkInfo(userId);
//        System.out.println("debug +++");
        JSONObject object = new JSONObject();
        object.put("baseInfo",userInfo);
        object.put("interests",userInfo);
        object.put("profession",userInfo);
        object.put("pagerank",userInfo);

        PrintWriter out = response.getWriter();
		out.write(object.toString());
        out.close();  
    }
 
}