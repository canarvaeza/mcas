package mcas.KGraph;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

public class Queries {
	/** inserts **/

	/**
	 * The Idea of this function is to save data to a single graph ex, only data to
	 * person# graph.
	 * 
	 * @param graphOrgs
	 *            -> The graph in virtuoso
	 * @param graphToUpdate
	 *            -> The graph in which you want to save info
	 * @param queryContent
	 *            -> The content to save (only the RDF triplets content)
	 * @return True if info is saved.
	 */
	public static boolean insertNonConnectedData(VirtGraph vGraph, String graphToUpdate, String queryContent) {

		System.out.println("\n creating user...");
		String str = "INSERT INTO GRAPH <" + graphToUpdate + "> { " + queryContent + " }";
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, vGraph);
		vur.exec();

		return true;
	}

	public static boolean insertConnectedData(VirtGraph vGraph, String queryContent) {

		// queryContent = "\r\n" + "select distinct * from
		// <http://localhost:8890/mcas/person#> where {?s ?p ?o.} LIMIT 100";

		System.out.println(queryContent);
		System.out.println("\n creating content...");
		String str = "insert data\r\n" + "{" + queryContent + "}";

		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, vGraph);
		vur.exec();

		return true;
	}

	/**
	 * 
	 * @param vGraph
	 * @param graphToConsult
	 * @param limit
	 * @return all data from a graph
	 */
	public static String getGraphData(VirtGraph vGraph, String graphToConsult, Long limit) {

		Query sparql = QueryFactory.create("SELECT * FROM <" + graphToConsult + "> WHERE { ?s ?p ?o }");
		if (limit != null) {
			System.out.println("Is not null");
			sparql.setLimit(limit);
		}

		System.out.println("query created");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, vGraph);
		System.out.println("query executed");

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode graphName = result.get("graph");
			RDFNode s = result.get("s");
			RDFNode p = result.get("p");
			RDFNode o = result.get("o");
			System.out.println(s + " " + p + " " + o);
		}

		System.out.println("executed");

		// http://localhost:8890/mcas/person#
		return "";
	}

	/**
	 * 
	 * @param vGraph
	 * @param graphToConsult
	 * @param subToConsult
	 * @param limit
	 * @return specific data from a graph
	 */
	public static String getGraphData(VirtGraph vGraph, String graphToConsult, String subToConsult, Long limit) {

		String response = "";

		Query sparql = QueryFactory
				.create("SELECT * FROM <" + graphToConsult + "> WHERE { <" + subToConsult + "> ?p ?o }");
		if (limit != null) {
			System.out.println("Is not null");
			sparql.setLimit(limit);
		}

		System.out.println("query created");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, vGraph);
		System.out.println("query executed");

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode graphName = result.get("graph");
			// RDFNode s = result.get("s");
			RDFNode p = result.get("p");
			RDFNode o = result.get("o");
			response += "<" + subToConsult + ">" + " " + p + " " + o + ".\n";
			// System.out.println(s + " " + p + " " + o );
		}

		System.out.println("executed");
		return response;

	}

	
	/**
	 * 
	 * @param vGraph
	 * @param graphToConsult
	 * @param subToConsult
	 * @param limit
	 * @return specific data from a graph
	 */
	public static String getSpecificGraphData(VirtGraph vGraph, String returnParameters, String graphToConsult, String subToConsult, Long limit) {

		String response = "";
		//String lowActivities = "";
		Query sparql = QueryFactory
				//.create("SELECT" + returnParameters +" FROM <" + graphToConsult + "> WHERE { " + subToConsult + "} order by ?s");
				.create("prefix xsd:<http://www.w3.org/2001/XMLSchema#>\r\n" + 
						"prefix mcas:<http://localhost:8890/mcas/>\r\n" + 
						"select distinct ?rule ?constructor ?select ?lowActivity\r\n" + 
						"\r\n" + 
						"from <http://localhost:8890/mcas/rules#>\r\n" + 
						"from named <http://localhost:8890/mcas/activity#>\r\n" + 
						"WHERE\r\n" + 
						"	{\r\n" + 
						"\r\n" + 
						"    ?rule  <http://purl.org/rules/activities#hasTrigger>  ?trigger;\r\n" + 
						"<http://purl.org/rules/activities#hasConstructor> ?constructor;\r\n" + 
						"<http://purl.org/rules/activities#hasSelect> ?select\r\n" + 
						"\r\n" + 
						"GRAPH <http://localhost:8890/mcas/activity#> { \r\n" + 
						"\r\n" + 
						"		SELECT DISTINCT  *\r\n" + 
						"		WHERE\r\n" + 
						"			{\r\n" + 
						"\r\n" + 
						"				?lowActivity  a   ?trigger;\r\n" + 
						"					<http://purl.org/m-context/ontologies/time#hasBeginningTime> ?begTime.\r\n" + 
						"\r\n" + 
						"				BIND (\"2017-06-07\"^^xsd:dateTime as ?day).\r\n" + 
						"				BIND (bif:datediff ('day',  ?day, ?begTime) as ?dayDifference).\r\n" + 
						"				FILTER (?dayDifference = 0)\r\n" + 
						"\r\n" + 
						"			}\r\n" + 
						"    	}\r\n" + 
						"	}\r\n" + 
						"ORDER BY ?lowActivity");
		if (limit != null) {
			System.out.println("Is not null");
			sparql.setLimit(limit);
		}

		System.out.println("query created");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, vGraph);
		System.out.println("query executed");

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode graphName = result.get("graph");
			// RDFNode s = result.get("s");
			RDFNode rule = result.get("rule");
			RDFNode constructor =result.get("constructor");
			RDFNode select = result.get("select");
			response +=  rule + "\n" + constructor + "\n" + select + "\n";
			System.out.println(rule + "\n" + constructor + "\n" + select + "\n");
		}

		System.out.println("executed");
		return response;

	}
	
	
	
	
	public static String getDataInRange(VirtGraph vGraph, String graphToConsult, Long limit, String range) {
		range = "Filter(?o > xsd:date(\"2017-06-05\") && ?o < xsd:date(\"2017-06-07\"))";
		Query sparql = QueryFactory.create("SELECT * FROM <" + graphToConsult + "> WHERE { ?s ?p ?o"+ range +" }");
		if (limit != null) {
			System.out.println("Is not null");
			sparql.setLimit(limit);
		}

		System.out.println("query created");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, vGraph);
		System.out.println("query executed");

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode graphName = result.get("graph");
			RDFNode s = result.get("s");
			RDFNode p = result.get("p");
			RDFNode o = result.get("o");
			System.out.println(s + " " + p + " " + o);
		}

		System.out.println("executed");

		// http://localhost:8890/mcas/person#
		return "";
	}

}
