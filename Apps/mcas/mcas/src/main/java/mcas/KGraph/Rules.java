package mcas.KGraph;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.text.AbstractDocument.Content;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import com.opencsv.CSVReader;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

/**
 * Rules
 */
public class Rules {

	public static boolean insert_all_rules_sparql(VirtGraph vGraph, String dir) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(dir));
                CSVReader csvReader = new CSVReader(reader);
            ) {
                // Reading Records One by One in a String array
            	String[] header = csvReader.readNext();
                String[] nextRecord = null;
                

                while ((nextRecord = csvReader.readNext()) != null) {
            	    HashMap<String, String> rule_content = new HashMap<String, String>();
                    for (int i = 0; i < header.length; i++) {
                    	rule_content.put(header[i], nextRecord[i]);
    				}
    	    		Rules.create_new_rule(vGraph, rule_content);
                }
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        return true;
		
	}
	
    public static String create_activity_String(String activities) {
        
        int activities_counter = 1;
        String activity_string = "";
        for (String activity : activities.split(";")) {
            String activity_template = 		"?act<*n*> a <*type*>;\r\n" + 
            "time:hasBeginningTime ?btime<*n*>;\r\n" + 
            "time:hasEndingTime ?etime<*n*>.\r\n" + 
            "FILTER (xsd:date(\"<*date_before*>\") < ?btime<*n*> && ??btime<*n*> < xsd:date(\"<*date*>\") )";

            activity_template = activity_template.replace("<*n*>", Integer.toString(activities_counter));
            activity_template = activity_template.replace("<*type*>", activity);

            activity_string = activity_string.concat(String.format("%s\n\n", activity_template));
        }
        return activity_string;
    }
    
    public static String create_content_from_split(String type, String content, boolean end_in_point) {
		String final_content = "";
		for (String element : content.split(";")) {
			final_content = final_content.concat(String.format("%s %s", type, element));
			if (end_in_point) {
				final_content += ".\n";
			}
			else
			{
				final_content += '\n';
			}
			
		}
		return final_content;
	}
    
    public static boolean create_new_rule(VirtGraph vGraph, HashMap<String, String> rule_content) {
    	
    	System.out.println(rule_content.toString());
    	
    	String rule_prefixes = rule_content.getOrDefault("rule_prefixes", ": <>");
    	rule_content.remove("rule_prefixes");
    	String rule_graph = rule_content.getOrDefault("graph", ": <>");
    	rule_content.remove("graph");
    	String preference_value = rule_content.getOrDefault("preference_value", ": <>");
    	rule_content.remove("preference_value");
    	
    	String activities = rule_content.getOrDefault("activities", "");
    	String new_activity_class = rule_content.getOrDefault("new_activity_class", "");
    	String content = Rules.create_rule_content(rule_content);
    	
    	String graph_plus_rule_id = rule_graph.replace(">", "rule/0007");
    	
    	String rule_graph_template = "	graph <*graph*>\r\n" + 
    			"	{\r\n" + 
    			"		<*graph+rule_id*>> a rules_ont:Rule.\r\n" + 
    			"       <*triger*>\r\n" + 
    			"		<*result*>\r\n" + 
    			"		<*graph+rule_id*>> rules_ont:hasPreferenceValue <*preference_value*>.\r\n" + 
    			"		<*graph+rule_id*>> rules_ont:hasContent \"\"\"<*content*>\"\"\".\r\n" + 
    			"	}";
    	
    	rule_graph_template = rule_graph_template.replace("<*graph*>", rule_graph);
    	rule_graph_template = rule_graph_template.replace("<*graph+rule_id*>", graph_plus_rule_id);
    	rule_graph_template = rule_graph_template.replace("<*triger*>", create_content_from_split(graph_plus_rule_id + "> rules_ont:hasTrigger", activities, true));
    	rule_graph_template = rule_graph_template.replace("<*result*>", create_content_from_split(graph_plus_rule_id + "> rules_ont:hasResult ", new_activity_class, true));
    	rule_graph_template = rule_graph_template.replace("<*preference_value*>", preference_value);
    	rule_graph_template = rule_graph_template.replace("<*content*>", content);
    	
    	String rule_template = "<*prefixes*>\r\n" + 
    			"INSERT DATA\r\n" + 
    			"{\r\n" + 
    			"    <*rule_graph*>\r\n" + 
    			"}";
    	
    	rule_template = rule_template.replace("<*prefixes*>", create_content_from_split("prefix", rule_prefixes, false));
    	rule_template = rule_template.replace("<*rule_graph*>", rule_graph_template);
    	
    	System.out.println(rule_template);
    	
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(rule_template, vGraph);
		vur.exec();
		
    	return true;
	}
    
    public static String create_rule_content(HashMap<String, String> rule_content) {
		
    	String prefixes = rule_content.getOrDefault("content_prefixes", "");
    	String activity_prefix = rule_content.getOrDefault("activity_prefix", "");
    	String new_activity_class = rule_content.getOrDefault("new_activity_class", "");
    	String from = rule_content.getOrDefault("from", "");
    	String activities = rule_content.getOrDefault("activities", "");
    	// date and date_before shouldn't go cause that value should change according to the date in execution time
//    	String date = rule_content.getOrDefault("prefixes", "");
//    	String date_before = rule_content.getOrDefault("prefixes", "");

        System.out.println("\n creating activity rule...");
        String ruleTemplate = "<*prefixes*>\r\n" + 
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

        ruleTemplate = ruleTemplate.replace("<*prefixes*>", create_content_from_split("prefix", prefixes, false));
        ruleTemplate = ruleTemplate.replace("<*activity_prefix*>", activity_prefix);
        ruleTemplate = ruleTemplate.replace("<*new_activity_class*>", new_activity_class);
        ruleTemplate = ruleTemplate.replace("<*from*>", create_content_from_split("from", from, false));
        ruleTemplate = ruleTemplate.replace("<*activity*>", create_activity_String(activities));

//        System.out.println(ruleTemplate);
		return ruleTemplate;
    }

}