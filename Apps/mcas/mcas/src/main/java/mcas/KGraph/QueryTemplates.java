package mcas.KGraph;

import java.util.HashMap;
import java.util.Map;

public class QueryTemplates {
	
	final static String activity_template = "?act<*n*> a <*type*>;\r\n" + 
            "time:hasBeginningTime ?btime<*n*>;\r\n" + 
            "time:hasEndingTime ?etime<*n*>.\r\n" + 
            "FILTER (xsd:date(\"<*date_before*>\") < ?btime<*n*> && ?btime<*n*> < xsd:date(\"<*date*>\") )";
	
	final static String place_template = "?act1 <http://purl.org/m-context/ontologies/mContext#isDescribedBy> ?desc.\r\n" + 
			"\r\n" + 
			"# -- this to get info from media location --\r\n" + 
			"optional{\r\n" + 
			"?desc <http://www.w3.org/ns/ma-ont#isMultimediaDescriptor> ?media.\r\n" + 
			"?media <http://purl.org/m-context/ontologies/mContext#hasCreator> ?sensor.\r\n" + 
			"?sensor <http://www.geonames.org/ontology#locatedIn> ?location.\r\n" + 
			"?location a ?location_type\r\n" + 
			"}\r\n" + 
			"# -- this to get sensor location ---\r\n" + 
			"optional{\r\n" + 
			"?desc  <http://www.w3.org/ns/sosa/madeBySensor> ?sensor.\r\n" + 
			"?sensor <http://www.geonames.org/ontology#locatedIn> ?location.\r\n" + 
			"?location a ?location_type\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"filter(?location_type = <*location_type*>)\r\n" + 
			"";
	
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
	
	final static String person_template = "{ SELECT ?act1          # do a check for the selected subject\r\n" + 
			"    WHERE {?act1 <http://purl.org/m-context/ontologies/mContext#hasActor> ?o }\r\n" + 
			"    GROUP BY ?act1            # count properties per subject\r\n" + 
			"    HAVING (COUNT(?o) <*person_quantity*>) # only subjects with less than 5 properties \r\n" + 
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
			"    time:hasEndingTime ?etime1;\r\n" + 
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
			"OPTIONAL {\r\n" + 
			"?act1 <http://purl.org/m-context/ontologies/mContext#hasActor> ?user.\r\n" + 
			"}" + 
			"\r\n" + 
			"  <*place*>\r\n" + 
			"\r\n" + 
			"  <*person*>" + 
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
			put("place_template", place_template);
			put("person_template", person_template);
			put("rule_graph_template", rule_graph_template);
			put("rule_template", rule_template);
			put("rule_content", rule_content);
		}
	};
}
