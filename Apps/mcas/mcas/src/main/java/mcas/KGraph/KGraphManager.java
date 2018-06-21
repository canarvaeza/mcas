package mcas.KGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

public class KGraphManager {

	public static Map<String, String> map = new HashMap<String, String>() {
		{
			put("insert", "graph <*graph*>\r\n" + "	{\r\n" + "<*content*>\r\n" + "	}");
		}
	};

	public static void insertIntoKG(String stream) {
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
		
		executeQuery("insert", output);
		 //System.out.println(output);

	}

	public static String createQuery(String type, String graph, String content) {

		String query = map.get(type);
		query = query.replace("<*graph*>", getBeforeString("#", graph) + "#>").replace("<*content*>", content);
		return query;
	}

	public static void executeQuery(String type, String queryContent) {

		//queryContent = "\r\n" + "select distinct * from <http://localhost:8890/mcas/person#> where {?s ?p ?o.} LIMIT 100";
        
		queryContent = "insert data\r\n" + 
				"{" +
				queryContent +
				"}";
		
		String endPoint = "http://192.168.99.100:8890/sparql";

		Query query = QueryFactory.create(queryContent);

		QueryExecution queryExec = QueryExecutionFactory.sparqlService(endPoint, query);

		try {
			ResultSet results = queryExec.execSelect();
			ResultSetFormatter.out(System.out, results, query);

			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				RDFNode ontUri = soln.get("ont");
				Literal name = soln.getLiteral("name");
				Literal acr = soln.getLiteral("acr");
				System.out.println(ontUri + " ---- " + name + " ---- " + acr);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			queryExec.close();
		}
	}

	static String getBeforeString(String value, String stream) {
		int posA = stream.indexOf(value);
		if (posA == -1) {
			return "";
		}
		return stream.substring(0, posA);
	}

}
