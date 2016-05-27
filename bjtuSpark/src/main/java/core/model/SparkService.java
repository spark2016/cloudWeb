package core.model;

import javax.servlet.http.HttpServlet;

import core.tools.SshTool;

public class SparkService {

	private SshTool sshTool = new SshTool();
	
	public String getSparkInfo(String userId) {
		//处理真正的数据，获取spark返回数据
		sshTool.sshConnection();
        return "json info";
	}
	
}
