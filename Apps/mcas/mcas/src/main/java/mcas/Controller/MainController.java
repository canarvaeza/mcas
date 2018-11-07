package mcas.Controller;

import mcas.DataTransformation.ToRDF;
import mcas.KGraph.KGraphManager;
import mcas.KGraph.QueryConf;
import mcas.KGraph.Rules;
import virtuoso.jena.driver.VirtGraph;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainController {

	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";
	private static final String PROJECT_PATH = System.getProperty("user.dir");
	private static final String RESOURCES_PATH = Paths.get(PROJECT_PATH,"src/main/resources/").toString();

	public static void main(String[] args) throws Exception {
//		String rdfFlux = ToRDF.data2RDF(Paths.get(RESOURCES_PATH,"transformation").toString(), "mcas-model-03.rml.ttl", "salida.ttl");

		// String rdfFlux = "";

		// generate random UUIDs
//		UUID idOne = UUID.randomUUID();
//		UUID idTwo = UUID.randomUUID();
//		System.out.println("UUID One: " + idOne);
//		System.out.println("UUID Two: " + idTwo);
		
		
		
		// String rdfFlux = "<http://localhost:8890/mcas/person#per/" + idOne
		// + "> a <http://purl.org/m-context/ontologies/person#PersonMCAS>.\r\n"
		// + "<http://localhost:8890/mcas/person#per/" + idOne + "> foaf:firstName
		// \"Nicol\".\r\n"
		// + "<http://localhost:8890/mcas/person#per/" + idOne + "> foaf:lastName
		// \"Narvaez\".\r\n"
		// + "<http://localhost:8890/mcas/person#per/" + idOne + "> foaf:gender
		// \"female\".\r\n"
		// + "<http://localhost:8890/mcas/person#per/" + idOne + "> foaf:accountName
		// \"nicole89\".\r\n"
		// + "<http://localhost:8890/mcas/person#per/" + idOne + "> foaf:age 7.";

//		 System.out.println(rdfFlux);

		// CREATE THE VIRTUAL GRAPH

		// QueryConf.queryGraphBase + "person#" first If I want to use specific graph
		VirtGraph graph = new VirtGraph(QueryConf.connectionString, QueryConf.userName, QueryConf.passWord);
//		KGraphManager.insertConnectedData(graph, rdfFlux);

		KGraphManager.getRules(graph, "2017-06-07");
		
		Rules.insert_all_rules_sparql(graph, Paths.get(RESOURCES_PATH, "rules", "rules.csv").toString());

	}
}
