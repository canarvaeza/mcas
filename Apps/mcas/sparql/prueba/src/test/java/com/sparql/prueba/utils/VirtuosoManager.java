package com.sparql.prueba.utils;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

public class VirtuosoManager {

	public static void main(String[] args) {
		String queryString = "PREFIX f: <http://example.org#>\n" + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + "select *\n" + "where {\n"
				+ "?person rdf:type foaf:Person\n" + "} LIMIT 100";

		Query query = QueryFactory.create(queryString);

		QueryExecution queryExec = QueryExecutionFactory.sparqlService("http://192.168.99.100:8890/sparql", query);

		try {
			ResultSet results = queryExec.execSelect();
			ResultSetFormatter.out(System.out, results, query);

//			for (; results.hasNext();) {
//				QuerySolution soln = results.nextSolution();
//				RDFNode ontUri = soln.get("ont");
//				Literal name = soln.getLiteral("name");
//				Literal acr = soln.getLiteral("acr");
//				System.out.println(ontUri + " ---- " + name + " ---- " + acr);
//			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			queryExec.close();
		}

	}

}
