package mcas.KGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import virtuoso.jena.driver.*;

import mcas.KGraph.QueryConf;
import mcas.util.DatesManager;


public class KGraphManager {
	
	public static Map<String, String> map = new HashMap<String, String>() {
		{
			put("insert", "graph <*graph*>\r\n" + "	{\r\n" + "<*content*>\r\n" + "	}");
		}
	};
	
	public static boolean insertPerson(VirtGraph vGraph, String queryContent) {
		String graphToUpdate = QueryConf.graphsBases.get("person");
		Queries.insertNonConnectedData(vGraph, graphToUpdate, queryContent);
		return true;
	}
	
	public static boolean insertConnectedData(VirtGraph vGraph, String stream) {
		String output = "";

		try {
			Scanner scanner = new Scanner(stream);
			String rdfFragment = null;
			String actualString = "";

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (actualString.equals(getBeforeString(" ", line))) {
					rdfFragment += line + "\n";
				} else {
					if (rdfFragment != null) {
						output += createQuery("insert", actualString, rdfFragment);
					}
					actualString = getBeforeString(" ", line);
					rdfFragment = "" + line + "\n";
				}
			}
			output += createQuery("insert", actualString, rdfFragment);
				
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
//		System.out.println(output);
		 
		Queries.insertConnectedData(vGraph, output);
		return true;
	}
	
	public static String getPerson(VirtGraph vGraph, String id) {
		String graphToConsult = QueryConf.graphsBases.get("person");
		String subToConsult = graphToConsult + "per/" + id;
		String response = Queries.getGraphData(vGraph, graphToConsult, subToConsult, null);
		return response;
	}
	
	public static List<String> getRules(VirtGraph vGraph, String date) {
		String graphToConsult = QueryConf.graphsBases.get("rules");
		String subToConsult = "prefix xsd:<http://www.w3.org/2001/XMLSchema#> \r\n" + 
				"prefix mcas:<http://localhost:8890/mcas/> \r\n" + 
				"select distinct ?rule ?content\r\n" + 
				"from <http://localhost:8890/mcas/rules#> \r\n" + 
				"from named <http://localhost:8890/mcas/activity#> \r\n" + 
				"WHERE \r\n" + 
				"	{ \r\n" + 
				" \r\n" + 
				"    ?rule  <http://purl.org/rules/activities#hasTrigger>  ?trigger; \r\n" + 
				"<http://purl.org/rules/activities#hasContent> ?content.\r\n" + 
				" \r\n" + 
				"GRAPH <http://localhost:8890/mcas/activity#> {  \r\n" + 
				" \r\n" + 
				"		SELECT DISTINCT  * \r\n" + 
				"		WHERE \r\n" + 
				"			{ \r\n" + 
				" \r\n" + 
				"				?lowActivity  a   ?trigger; \r\n" + 
				"					<http://purl.org/m-context/ontologies/time#hasBeginningTime> ?begTime. \r\n" + 
				"# un d�a antes que el d�a que quiero, el d�a que quiero \r\n" + 
				"\r\n" + 
				"				FILTER (xsd:date(\"<*date_before*>\") < ?begTime  && ?begTime < xsd:date(\"<*date*>\") ) \r\n" + 
				" \r\n" + 
				"			} \r\n" + 
				"    	} \r\n" + 
				"	} \r\n" + 
				"ORDER BY ?lowActivity";
		String date_before = DatesManager.getNewDate(date, -1);
		subToConsult = subToConsult.replace("<*date*>", date);
		subToConsult = subToConsult.replace("<*date_before*>", date_before);
//		System.out.println(subToConsult);
		List<String> response = Queries.getUsefulRulesContent(vGraph, subToConsult, date, date_before, null);
		return response;
	}
	
	public static String getAllGraph(VirtGraph vGraph, String graphToConsult) {
		String response = Queries.getGraphData(vGraph, QueryConf.graphsBases.get(graphToConsult), null);
		return response;
	}

	public static String createQuery(String type, String graph, String content) {
		String query = map.get(type);
		query = query.replace("<*graph*>", getBeforeString("#", graph) + "#>").replace("<*content*>", content);
		return query;
	}

	static String getBeforeString(String value, String stream) {
		int posA = stream.indexOf(value);
		if (posA == -1) {
			return "";
		}
		return stream.substring(0, posA);
	}

}
