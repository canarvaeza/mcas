package mcas.KGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

import virtuoso.jena.driver.*;

import mcas.KGraph.QueryConf;


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

		try (Scanner scanner = new Scanner(stream)) {

			String rdfFragment = null;
			String actualString = "";

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (actualString.equals(getBeforeString(" ", line)) && scanner.hasNextLine()) {
					rdfFragment += line + "\n";
				} else {
					if (rdfFragment != null) {
						output += createQuery("insert", actualString, rdfFragment);
					}
					actualString = getBeforeString(" ", line);
					rdfFragment = "" + line + "\n";
				}
			}
		}
		
		System.out.println(output);
		 
		Queries.insertConnectedData(vGraph, output);
		return true;
	}
	
	public static String getPerson(VirtGraph vGraph, String id) {
		String graphToConsult = QueryConf.graphsBases.get("person");
		String subToConsult = graphToConsult + "per/" + id;
		String response = Queries.getGraphData(vGraph, graphToConsult, subToConsult, null);
		return response;
	}
	
	public static String getRules(VirtGraph vGraph, String date) {
		String graphToConsult = QueryConf.graphsBases.get("rules");
		String subToConsult = "prefix xsd:<http://www.w3.org/2001/XMLSchema#> \r\n" +
				"prefix bif:<bif:>  \r\n"+
				"prefix mcas:<http://localhost:8890/mcas/> \r\n" + 
				"select distinct ?rule ?constructor ?select \r\n" + 
				"from <http://localhost:8890/mcas/rules#> \r\n" + 
				"from named <http://localhost:8890/mcas/activity#> \r\n" + 
				"WHERE \r\n" + 
				"	{ \r\n" + 
				" \r\n" + 
				"    ?rule  <http://purl.org/rules/activities#hasTrigger>  ?trigger; \r\n" + 
				"<http://purl.org/rules/activities#hasConstructor> ?constructor; \r\n" + 
				"<http://purl.org/rules/activities#hasSelect> ?select \r\n" + 
				" \r\n" + 
				"GRAPH <http://localhost:8890/mcas/activity#> {  \r\n" + 
				" \r\n" + 
				"		SELECT DISTINCT  * \r\n" + 
				"		WHERE \r\n" + 
				"			{ \r\n" + 
				" \r\n" + 
				"				?lowActivity  a   ?trigger; \r\n" + 
				"					<http://purl.org/m-context/ontologies/time#hasBeginningTime> ?begTime. \r\n" + 
				"				 \r\n" + 
				"				BIND (\"<YY-MM-DD>\"^^xsd:date as ?day). \r\n" + 
				"				BIND (bif:datediff ('day',  ?day, ?begTime) as ?dayDifference). \r\n" + 
				"				FILTER (?dayDifference = 0) \r\n" + 
				" \r\n" + 
				"			} \r\n" + 
				"    	} \r\n" + 
				"	} \r\n" + 
				"ORDER BY ?lowActivity";
		subToConsult = subToConsult.replace("<YY-MM-DD>", date);
//		System.out.println(subToConsult);
		String response = Queries.getSpecificGraphData(vGraph, "distinct ?o ?c ?t", graphToConsult, subToConsult, null);
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
