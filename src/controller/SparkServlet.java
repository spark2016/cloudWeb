package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SparkHandler;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class sparkServlet
 */
@WebServlet("/sparkServlet")
public class SparkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SparkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		SparkHandler handler = new SparkHandler();
        //TODO
		String userInfo = handler.getSparkInfo(userId);
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
