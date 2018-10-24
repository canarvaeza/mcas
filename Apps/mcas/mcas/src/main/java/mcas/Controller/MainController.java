package mcas.Controller;

import mcas.DataTransformation.ToRDF;
import mcas.KGraph.KGraphManager;
import mcas.KGraph.QueryConf;
import mcas.KGraph.Rules;
import virtuoso.jena.driver.VirtGraph;

import java.util.HashMap;
import java.util.Map;
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
//		VirtGraph graph = new VirtGraph(QueryConf.connectionString, QueryConf.userName, QueryConf.passWord);

//		KGraphManager.insertConnectedData(graph, rdfFlux);
		
//		KGraphManager.getRules(graph);
		
	    HashMap<String, String> rule_content = new HashMap<String, String>() {
	    	{
	    		put("prefixes", ": <http://purl.org/m-context/ontologies/mContext#>,time: <http://purl.org/m-context/ontologies/time#>,xsd:<http://www.w3.org/2001/XMLSchema#>");
	    		put("activity_prefix", "<http://localhost:8890/mcas/activity#act/>");
	    		put("new_activity_class", "alzheimer:Nocturia");
	    		put("from", "<http://localhost:8890/mcas/person#>, <http://localhost:8890/mcas/activity#>");
	    		put("activities", "<http://purl.org/m-context/ontologies/domains/alzheimer#WakeUp>, <http://purl.org/m-context/ontologies/domains/alzheimer#Bathroom>");
//	    		put("date", "2017-06-07");
//	    		put("date_before", "2017-06-06");
	    	}
	    };

		Rules.create_new_activity_rule(rule_content);
		

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
		// // debo tratar de tener la clase representada en alg�n lugar.
		// // ---------------------
		//
		// // OWLOntology ontology = OntologyManager.loadOntology(root,
		// // "ont/contextSWRL.owl");
		//
		// // CREO QUE YA PUEDO DEJAR SOLO UNA FUNCI�N
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
