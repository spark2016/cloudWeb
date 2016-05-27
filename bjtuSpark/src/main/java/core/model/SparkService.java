package core.model;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import core.tools.SshTool;

@Service
public class SparkService {

	@Autowired
	private SshTool sshTool;
	
	public String getSparkInfo(String userId) {
		//处理真正的数据，获取spark返回数据
		sshTool.sshSession();
        return "json info";
	}
	
}
