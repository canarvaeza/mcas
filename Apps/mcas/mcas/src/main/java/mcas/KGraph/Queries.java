package mcas.KGraph;

import java.util.ArrayList;
import java.util.List;

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
	
	public static boolean constructNewRule(VirtGraph vGraph, String graphToUpdate, List<String> queries, String date, String date_before) {
		for (String queryContent : queries) {
			try {
				/*queryContent = "		prefix : <http://purl.org/m-context/ontologies/mContext#>\r\n" + 
				"			prefix activity: <http://localhost:8890/mcas/activity#act/>\r\n" + 
				"			prefix time: <http://purl.org/m-context/ontologies/time#>\r\n" + 
				"\r\n" + 
				"			INSERT\r\n" + 
				"			{\r\n" + 
				"				GRAPH  <http://localhost:8890/mcas/activity#> {\r\n" + 
				"					?new a alzheimer:Nocturia;\r\n" + 
				"					    :hasSubActivity ?act1;\r\n" + 
				"					    :hasSubActivity ?act2;\r\n" + 
				"					    time:hasBeginningTime ?bt1;\r\n" + 
				"					    time:hasEndingTime ?et1;\r\n" + 
				"					    :hasActor ?user.\r\n" + 
				"					?act1 :isSubActivity ?new.\r\n" + 
				"					?act2 :isSubActivity ?new.\r\n" + 
				"				}\r\n" + 
				"				GRAPH  <http://localhost:8890/mcas/person#> {\r\n" + 
				"					?user :isInvolvedIn ?new.\r\n" + 
				"				}\r\n" + 
				"			}			\r\n" + 
				"from <http://localhost:8890/mcas/person#>\r\n" + 
				"			from <http://localhost:8890/mcas/activity#>\r\n" + 
				"\r\n" + 
				"			where {\r\n" + 
				"\r\n" + 
				"				?act1 a <http://purl.org/m-context/ontologies/domains/alzheimer#WakeUp>;\r\n" + 
				"				time:hasBeginningTime ?b1;\r\n" + 
				"				time:hasEndingTime ?e1.\r\n" + 
				"				FILTER (xsd:date(\"2017-06-06\") < ?b1&& ?b1< xsd:date(\"2017-06-07\") )\r\n" + 
				"\r\n" + 
				"				?act2 a <http://purl.org/m-context/ontologies/domains/alzheimer#Bathroom>;\r\n" + 
				"				time:hasBeginningTime ?b2;\r\n" + 
				"				time:hasEndingTime ?e2.\r\n" + 
				"				FILTER (xsd:date(\"2017-06-06\") < ?b2 && ?b2< xsd:date(\"2017-06-07\"))\r\n" + 
				"\r\n" + 
				"				#Contains the activity\r\n" + 
				"				FILTER (?b1 < ?b2 && ?e1 > ?e2).\r\n" + 
				"\r\n" + 
				"				optional {\r\n" + 
				"				?act2 :hasActor ?user.\r\n" + 
				"				}\r\n" + 
				"\r\n" + 
				"				BIND (URI(CONCAT(\r\n" + 
				"				str(activity:), \r\n" + 
				"				STRAFTER(str(?act1), str(activity:))\r\n" + 
				"				,\"_\", \r\n" + 
				"				STRAFTER(str(?act2), str(activity:))\r\n" + 
				"				)) as ?new).\r\n" + 
				"				FILTER(NOT EXISTS {?new a [] .})  \r\n" + 
				"\r\n" + 
				"			}\r\n" + 
				"";*/
				queryContent = queryContent.replace("\\\"", "\"");
				queryContent = queryContent.replace("<*date*>", date);
				queryContent = queryContent.replace("<*date_before*>", date_before);
				//		System.out.println(queryContent);
				VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(queryContent, vGraph);
				vur.exec();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		return true;

	}
	
	
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
			System.out.println(graphName);
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
	public static List<String> getUsefulRulesContent(VirtGraph vGraph, String subToConsult, String date, String date_before, Long limit) {
		
		List<String> rules_list = new ArrayList<String>();
		//String lowActivities = "";
		Query sparql = QueryFactory.create(subToConsult);
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
			String response = "";
			RDFNode rule = result.get("rule");
			RDFNode content =result.get("content");
			response += content + "\n";
//			System.out.println(rule + "\n" + content + "\n");
			rules_list.add(response);
		}

		System.out.println("executed");
				
		return rules_list;

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
