package mcas.KGraph;

import java.util.HashMap;
import java.util.Map;

public class QueryTemplates {
	
	final static String activity_template = "?act<*n*> a <*type*>;\r\n" + 
            "time:hasBeginningTime ?btime<*n*>;\r\n" + 
            "time:hasEndingTime ?etime<*n*>.\r\n" + 
            "FILTER (xsd:date(\"<*date_before*>\") < ?btime<*n*> && ??btime<*n*> < xsd:date(\"<*date*>\") )";
	
	final static String rule_graph_template = "	graph <*graph*>\r\n" + 
			"	{\r\n" + 
			"		<*graph+rule_id*>> a rules_ont:Rule.\r\n" + 
			"		<*triger*>\r\n" + 
			"		<*result*>\r\n" + 
			"		<*graph+rule_id*>> rules_ont:hasPreferenceValue \"<*preference_value*>\"^^xsd:integer.\r\n" + 
			"		<*graph+rule_id*>> rules_ont:hasContent \"\"\"<*content*>\"\"\".\r\n" + 
			"	}";
	
	final static String rule_template = "<*prefixes*>\r\n" + 
			"INSERT DATA\r\n" + 
			"{\r\n" + 
			"    <*rule_graph*>\r\n" + 
			"}";
	
	final static String rule_content = "<*prefixes*>\r\n" + 
			"prefix activity: <*activity_prefix*>\r\n" + 
			"\r\n" + 
			"INSERT{\r\n" + 
			"GRAPH  <http://localhost:8890/mcas/activity#> {\r\n" + 
			"  ?new a <*new_activity_class*>;\r\n" + 
			"    :hasSubActivity ?act1;\r\n" + 
			"    :hasSubActivity ?act2;\r\n" + 
			"    time:hasBeginningTime ?btime1;\r\n" + 
			"    time:hasEndingTime ?btime2;\r\n" + 
			"    :hasActor ?user.\r\n" + 
			"\r\n" + 
			"  ?act1 :isSubActivity ?new.\r\n" + 
			"  ?act2 :isSubActivity ?new.\r\n" + 
			" }\r\n" + 
			"  GRAPH  <http://localhost:8890/mcas/person#> {\r\n" + 
			"    ?user :isInvolvedIn ?new.\r\n" + 
			"  }\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"<*from*>\r\n" + 
			"\r\n" + 
			"WHERE {\r\n" + 
			"\r\n" + 
			"  <*activity*>\r\n" + 
			"\r\n" + 
			"	#Contains the activity\r\n" + 
			"	FILTER (?btime1 < ?btime2 && ?etime1 > ?etime2).\r\n" + 
			"  OPTIONAL {\r\n" + 
			"	  ?act2 :hasActor ?user.\r\n" + 
			"	}\r\n" +
			"\r\n" + 
			"	BIND (URI(CONCAT(\r\n" + 
			"	str(activity:), \r\n" + 
			"	STRAFTER(str(?act1), str(activity:))\r\n" + 
			"	,\"_\", \r\n" + 
			"	STRAFTER(str(?act2), str(activity:))\r\n" + 
			"	)) as ?new).\r\n" + 
			"	FILTER(NOT EXISTS {?new a [] .})\r\n" + 
	        "}";
	
	public static final Map<String, String> queryTemplates = new HashMap<String, String>(){
		{
			put("activity_template", activity_template);
			put("rule_graph_template", rule_graph_template);
			put("rule_template", rule_template);
			put("rule_content", rule_content);
		}
	};
}
