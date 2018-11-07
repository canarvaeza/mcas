package mcas.KGraph;

import java.util.HashMap;
import java.util.Map;

public class QueryConf {
	
	public static final String queryGraphBase = "http://localhost:8890/mcas/";
	public static final String connectionDir = "192.168.99.100:1111"; //el que se conecta con el 11
//	public static final String connectionDir = "172.31.128.1:32769";
	public static final String connectionString = "jdbc:virtuoso://"+connectionDir+"/charset=UTF-8/log_enable=2";// In docker is the virt port not the pc port
	public static final String userName = "dba";
	public static final String passWord = "123456";
//	public static final String passWord = "dba";
	
	
	// all the graphs in virtuoso
	public static final Map<String, String> graphsBases = new HashMap<String, String>() {
		{
			put("person", queryGraphBase + "person#");
			put("multimedia", queryGraphBase + "multimedia#");
			put("location", queryGraphBase + "location#");
			put("sensor", queryGraphBase + "sensor#");
			put("activity", queryGraphBase + "activity#");
			put("rules", queryGraphBase + "rules#" );
		}
	};
		
}
