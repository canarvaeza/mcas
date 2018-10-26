<<<<<<< HEAD
package mcas.Controller;

import mcas.DataTransformation.ToRDF;
import mcas.KGraph.KGraphManager;
import mcas.KGraph.QueryConf;
import virtuoso.jena.driver.VirtGraph;

import java.util.UUID;

public class MainController {

	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";

	public static void main(String[] args) throws Exception {

//		String rdfFlux = ToRDF.data2RDF(root + "transformation/", "mcas-model-03.rml.ttl", "salida.ttl");

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

		// System.out.println(rdfFlux);

		// CREATE THE VIRTUAL GRAPH

		// QueryConf.queryGraphBase + "person#" first If I want to use specific graph
		VirtGraph graph = new VirtGraph(QueryConf.connectionString, QueryConf.userName, QueryConf.passWord);

//		KGraphManager.insertConnectedData(graph, rdfFlux);
		
		KGraphManager.getRules(graph, "2017-06-07");
		

		//
		// public static OWLOntology domainOntology = OntologyManager
		// .loadOntologyFromInternet("http://purl.org/m-context/ontologies/mContext");
		//
		// public static void main(String[] args) {
		//
		// // // ---------- CREATE STREAM ------------
		//
		// String rdfFlux = ToRDF.data2RDF(root + "transformation/", "mcas-model.rml",
		// "salida.ttl");
		//
		// System.out.println(rdfFlux);
		//
		// // // --------------------------------------
		//
		// // // ------ TO ONTOLOGY ---------------- //
		//
		// // ----- LOAD FROM INTERNET
		// // OWLOntology domainOntology = OntologyManager
		// //
		// .loadOntologyFromInternet("http://purl.org/m-context/ontologies/mContext");
		//
		// // // ---- LOAD FROM PC FILE
		// // OWLOntology ontology = OntologyManager.loadOntology(root,
		// // "transformation/inputModified.rdf");
		//
		// // // ----- LOAD FROM STREAM
		// OWLOntology ontology = OntologyManager.loadOntology(rdfFlux);
		//
		// OntologyManager.getOntologyInfo(ontology);
		// OntologyManager.getOntologyInfo(domainOntology);
		// // OntologyManager.saveOntology(root, "salidaFlujo.ttl", ontology);
		//
		// // // ---------------------------------
		//
		// // ------- NOTA --------
		// // Puedo usar la merge o puedo usar solo el flujo de datos. De cualquier
		// forma
		// // debo tratar de tener la clase representada en algún lugar.
		// // ---------------------
		//
		// // OWLOntology ontology = OntologyManager.loadOntology(root,
		// // "ont/contextSWRL.owl");
		//
		// // CREO QUE YA PUEDO DEJAR SOLO UNA FUNCIÓN
		// // OWLOntology rdfInput = OntologyManager.loadStream(root,
		// // "rdf/rdfInputPersonas.rdf");
		//
		// // OWLOntology mergedOntology = OntologyManager.mergeOntologiesInManager();
		// //
		// // OntologyManager.getOntologyInfo(mergedOntology);
		//
		// // // ------------- RULES ----------------
		//
		// // OwlRules.getRulesList(ontology, root + "ont/" , "rulesNewVers.json");
		//
		// // OntologyManager.saveOntology(root, "ont/salidaNuevoSujeto.owl", ontology);
		//
		// // mcas.RulesManager.DroolsRules.executeDroolsRules();
		//
		// System.out.println("Finalizado");

	}
}
=======
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
		
//		KGraphManager.getRules(graph);
		
		Rules.insert_all_rules_sparql(graph, Paths.get(RESOURCES_PATH, "rules", "rules.csv").toString());

	}
}
>>>>>>> 2052d8b58f6a59e7b73127d4219e4a297a6e264a
