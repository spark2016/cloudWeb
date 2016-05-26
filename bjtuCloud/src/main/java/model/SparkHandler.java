package model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SparkHandler
 */
@WebServlet("/SparkHandler")
public class SparkHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SparkHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	public String getSparkInfo(String userId) {
		//处理真正的数据，获取spark返回数据
        return "json info";
	}

}
