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

		try {
			Scanner scanner = new Scanner(stream);
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
		} catch (Exception e) {
            e.printStackTrace(System.err);
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
	
	public static String getRules(VirtGraph vGraph) {
		String graphToConsult = QueryConf.graphsBases.get("rules");
		String subToConsult = "{\r\n" + 
				"		\r\n" + 
				"		select distinct ?s\r\n" + 
				"		from <http://localhost:8890/mcas/activity#>\r\n" + 
				"		where {\r\n" + 
				"			?o a ?s.\r\n" + 
				"			}\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	?o <http://purl.org/rules/activities#hasTrigger> ?t.\r\n" + 
				"	?o <http://purl.org/rules/activities#hasConstructor> ?c.\r\n" + 
				"";
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
