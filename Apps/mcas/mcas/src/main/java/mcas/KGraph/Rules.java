package mcas.KGraph;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

import com.opencsv.CSVReader;

import org.apache.commons.lang3.StringUtils;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;
/**
 * Rules
 */
public class Rules {

	public static boolean insert_all_rules_sparql(VirtGraph vGraph, String dir) {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(dir));
			CSVReader csvReader = new CSVReader(reader);
			// Reading Records One by One in a String array
			String[] header = csvReader.readNext();
			String[] nextRecord = null;


			while ((nextRecord = csvReader.readNext()) != null) {
				HashMap<String, String> rule_content = new HashMap<String, String>();
				for (int i = 0; i < header.length; i++) {
					rule_content.put(header[i], nextRecord[i]);
				}
				//                    System.out.println(rule_content.toString());
				Rules.create_new_rule(vGraph, rule_content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
		
	}
	
    public static String create_activity_String(String activities_string) {
        /*
         * ,,,,,,,Sin actividad,,,
,,,,,,,1 actividad,,,
,,,,,,COMO LAS VOY A SEPARAR DENTRO DE MI SISTEMA,>1 actividad,,,
         */
        int activities_counter = 1;
        String activity_string = "";
        String [] activities = activities_string.split(";");
        for (String activity : activities) {
            String activity_template = QueryTemplates.queryTemplates.get("activity_template");

            activity_template = activity_template.replace("<*n*>", Integer.toString(activities_counter));
            activity_template = activity_template.replace("<*type*>", activity);

            activity_string = activity_string.concat(String.format("%s\n\n", activity_template));
            activities_counter += 1;
        }
        if (activities.length > 1) {
        	String filter = "	#Contains the activity\r\n" + 
        			"	FILTER (?btime1 < ?btime2 && ?etime1 > ?etime2).";
        	activity_string = activity_string.concat(filter);
        }
        return activity_string;
    }
    
    /***
     * We suppose you only can be in one place so if multiple locations are provided they are separated with ORs ||
     * @param places
     * @return
     */
    public static String create_place_String(String places) {
        
        String [] placesList = places.split(";");
        
        String placeTemplate = QueryTemplates.queryTemplates.get("place_template");
        
        String filterPattern = StringUtils.substringBetween(placeTemplate, "filter(", ")");
        String filterString = "";
//        placeTemplate = placeTemplate.replace("<*n*>", Integer.toString(places_counter));
        for (int i = 0; i < placesList.length; i++) {
			String place = placesList[i];
        	String filter = filterPattern;
        	filter = filter.replace("<*location_type*>", place);
        	filterString += filter;
        	if (i != placesList.length-1) {
				filterString += " || ";
			}
        }
        placeTemplate = placeTemplate.replace(filterPattern, filterString);
        return placeTemplate;
    }
    
    public static String create_person_String(String person) {
    	String personTemplate = QueryTemplates.queryTemplates.get("person_template");
    	return personTemplate.replace("<*person_quantity*>", person);
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
    	
//    	System.out.println(rule_content.toString());
    	
    	String rule_prefixes = rule_content.getOrDefault("rule_prefixes", ": <>");
    	rule_content.remove("rule_prefixes");
    	String rule_graph = rule_content.getOrDefault("graph", ": <>");
    	rule_content.remove("graph");
    	String preference_value = rule_content.getOrDefault("preference_value", ": <>");
    	rule_content.remove("preference_value");
    	
    	String graph_plus_rule_id = rule_graph.replace(">", "rule/" + UUID.randomUUID());
    	
    	// Setting triggers
    	String trigger = "";
    	if (!rule_content.getOrDefault("activities", "").equals("")) {
    		String activities = create_content_from_split(graph_plus_rule_id + "> rules_ont:hasTrigger", rule_content.getOrDefault("activities", ""), true);
    		trigger += activities;
    	}
    	if (!rule_content.getOrDefault("sensor", "").equals("")) {
    		String sensor = create_content_from_split(graph_plus_rule_id + "> rules_ont:hasTrigger", rule_content.getOrDefault("sensor", ""), true);
    		trigger += sensor;
    	};
    	if (!rule_content.getOrDefault("place", "").equals("")) {
    		String place = create_content_from_split(graph_plus_rule_id + "> rules_ont:hasTrigger", rule_content.getOrDefault("place", ""), true);
    		trigger += place;
    	};
    	if (!rule_content.getOrDefault("person", "").equals("")) {
    		String person = "".concat(String.format("%s> rules_ont:hasTrigger \"person %s\" .", graph_plus_rule_id, rule_content.getOrDefault("person", "")));
    		trigger += person;
    	};
    	
    	String new_activity_class = rule_content.getOrDefault("new_activity_class", "");
    	String content = Rules.create_rule_content(rule_content);
    	

    	
    	String rule_graph_template = QueryTemplates.queryTemplates.get("rule_graph_template");

    	
    	rule_graph_template = rule_graph_template.replace("<*graph*>", rule_graph);
    	rule_graph_template = rule_graph_template.replace("<*graph+rule_id*>", graph_plus_rule_id);
    	rule_graph_template = rule_graph_template.replace("<*triger*>", trigger);
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
    	
    	
    	try {
            VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(rule_template, vGraph);
    		vur.exec();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(rule_template);
		}
    	return true;
	}
    
    public static String create_rule_content(HashMap<String, String> content) {
		
    	String prefixes = content.getOrDefault("content_prefixes", "");
    	String activity_prefix = content.getOrDefault("activity_prefix", "");
    	String new_activity_class = content.getOrDefault("new_activity_class", "");
    	String from = content.getOrDefault("from", "");

    	
    	// date and date_before shouldn't go cause that value should change according to the date in execution time
//    	String date = rule_content.getOrDefault("prefixes", "");
//    	String date_before = rule_content.getOrDefault("prefixes", "");

        System.out.println("\n creating activity rule...");
        String rule_content = QueryTemplates.queryTemplates.get("rule_content");

        rule_content = rule_content.replace("<*prefixes*>", create_content_from_split("prefix", prefixes, false));
        rule_content = rule_content.replace("<*activity_prefix*>", activity_prefix);
        rule_content = rule_content.replace("<*new_activity_class*>", new_activity_class);
        rule_content = rule_content.replace("<*from*>", create_content_from_split("from", from, false));
        
    	if (!content.getOrDefault("activities", "").equals("")) {
    		String activities = content.getOrDefault("activities", "");
    		rule_content = rule_content.replace("<*activity*>", create_activity_String(activities));
    	} else {
    		rule_content = rule_content.replace("<*activity*>", "");
    	};
    	
    	if (!content.getOrDefault("place", "").equals("")) {
    		String places = content.getOrDefault("place", "");
    		rule_content = rule_content.replace("<*place*>", create_place_String(places));
    	} else {
    		rule_content = rule_content.replace("<*place*>", "");
    	};
    	if (!content.getOrDefault("person", "").equals("")) {
    		String person = content.getOrDefault("person", "").replace("person ", "");
    		rule_content = rule_content.replace("<*person*>", create_person_String(person));
    	} else {
    		rule_content = rule_content.replace("<*person*>", "");
    	}

//        System.out.println(ruleTemplate);
		return rule_content;
    }

}