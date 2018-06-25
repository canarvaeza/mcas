package mcas.KGraph;

import java.util.HashMap;
import java.util.Map;

public class QueryConf {
	
	public static final String queryGraphBase = "http://localhost:8890/mcas/";
	public static final String connectionString = "jdbc:virtuoso://192.168.99.100:1111/charset=UTF-8/log_enable=2";// In docker is the virt port not the pc port
	public static final String userName = "dba";
	public static final String passWord = "123456";
	
	
	// all the graphs in virtuoso
	public static final Map<String, String> graphsBases = new HashMap<String, String>() {
		{
			put("person", queryGraphBase + "person#");
			put("multimedia", queryGraphBase + "multimedia#");
			put("location", queryGraphBase + "location#");
			put("sensor", queryGraphBase + "sensor#");
			put("activity", queryGraphBase + "activity#");
		}
	};
		
}
