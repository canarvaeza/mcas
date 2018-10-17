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
		UUID idOne = UUID.randomUUID();
		UUID idTwo = UUID.randomUUID();
		System.out.println("UUID One: " + idOne);
		System.out.println("UUID Two: " + idTwo);

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
		
		KGraphManager.getRules(graph);
		

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
