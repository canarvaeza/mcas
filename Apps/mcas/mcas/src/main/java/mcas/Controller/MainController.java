package mcas.Controller;

import mcas.DataTransformation.ToRDF;
import mcas.KGraph.KGraphManager;


import java.util.UUID;

public class MainController {
	
	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";
	
	public static void main(String[] args) throws Exception {

//		String rdfFlux = ToRDF.directData2RDF(root + "lib/", root + "transformation/", "mcas-model.rml");

//		String rdfFlux = ToRDF.data2RDF(root + "transformation/", "mcas-model-03.rml.ttl", "salida.ttl");
		
		/*String rdfFlux = "<http://localhost:8890/mcas/activity#act/00001> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://purl.org/m-context/ontologies/domains/alzheimer#Eat>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00001> <http://purl.org/m-context/ontologies/time#hasBeginningTime> \"2017-06-06T12:32:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00001> <http://purl.org/m-context/ontologies/time#hasEndingTime> \"2017-06-06T12:38:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00001> <http://purl.org/m-context/ontologies/mContext#hasActor> <http://localhost:8890/mcas/person#per/00001>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00001> <http://purl.org/m-context/ontologies/mContext#hasActor> <http://localhost:8890/mcas/person#per/00002>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00001> <http://purl.org/m-context/ontologies/mContext#isDescribedBy> <http://localhost:8890/mcas/multimedia#mul/00001>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00001> <http://purl.org/m-context/ontologies/mContext#isDescribedBy> <http://localhost:8890/mcas/sensor#obs/00001>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00002> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://purl.org/m-context/ontologies/domains/alzheimer#Sit>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00002> <http://purl.org/m-context/ontologies/time#hasBeginningTime> \"2017-06-06T12:30:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\r\n" + 
				"<http://localhost:8890/mcas/activity#act/00002> <http://purl.org/m-context/ontologies/time#hasEndingTime> \"2017-06-06T12:40:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\r\n" + 
				"<http://localhost:8890/mcas/sensor#obs/00001> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/sosa/Observation>.\r\n" + 
				"<http://localhost:8890/mcas/sensor#obs/00001> <http://www.w3.org/ns/sosa/madeBySensor> <http://localhost:8890/mcas/sensor#sen/35-207306-844818-0/BMP282>.\r\n" + 
				"<http://localhost:8890/mcas/sensor#obs/00001> <http://www.w3.org/ns/sosa/hasSimpleResult> \"[0.60034, 0.42556, 0.86662]\"^^<http://www.w3.org/2001/XMLSchema#decimal>.\r\n" + 
				"<http://localhost:8890/mcas/sensor#obs/00001> <http://www.w3.org/ns/sosa/resultTime> \"2017-06-06T12:36:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\r\n" + 
				"<http://localhost:8890/mcas/sensor#obs/00001> <http://purl.org/m-context/ontologies/mContext#describe> <http://localhost:8890/mcas/activity#act/00002>.\r\n" + 
				"<http://localhost:8890/mcas/sensor#obs/00001> <http://www.geonames.org/ontology#locatedIn> <http://localhost:8890/mcas/location#loc/00001>.\r\n" + 
				"<http://localhost:8890/mcas/multimedia#mul/00001> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.semanticweb.org/ontologies/multimedia#VideoFormat>.\r\n" + 
				"<http://localhost:8890/mcas/multimedia#mul/00001> <http://www.w3.org/ns/ma-ont#hasFormat> <http://AVI>.\r\n" + 
				"<http://localhost:8890/mcas/multimedia#mul/00001> <http://purl.org/m-context/ontologies/multimedia#creationDate> \"2017-06-06T12:36:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\r\n" + 
				"<http://localhost:8890/mcas/multimedia#mul/00001> <http://purl.org/m-context/ontologies/mContext#describe> <http://localhost:8890/mcas/activity#act00001>.\r\n" + 
				"<http://localhost:8890/mcas/multimedia#mul/00001> <http://www.geonames.org/ontology#locatedIn> <http://localhost:8890/mcas/location#loc/00001>.\r\n" + 
				"<http://localhost:8890/mcas/location#loc/00001> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://purl.org/m-context/ontologies/location#ClosedLocation>.\r\n" + 
				"<http://localhost:8890/mcas/location#loc/00001> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> \"40.414\".\r\n" + 
				"<http://localhost:8890/mcas/location#loc/00001> <http://www.w3.org/2003/01/geo/wgs84_pos#long> \"-3.699\".";*/		
		
		// generate random UUIDs
		UUID idOne = UUID.randomUUID();
		UUID idTwo = UUID.randomUUID();
		System.out.println("UUID One: " + idOne);
		System.out.println("UUID Two: " + idTwo);

		String rdfFlux = "<http://localhost:8890/mcas/person#per/" + idOne	+"> a <http://purl.org/m-context/ontologies/person#PersonMCAS>.\r\n"
				+ "<http://localhost:8890/mcas/person#per/" + idOne +"> foaf:firstName \"Nicol\".\r\n"
				+ "<http://localhost:8890/mcas/person#per/" + idOne +"> foaf:lastName \"Narvaez\".\r\n"
				+ "<http://localhost:8890/mcas/person#per/" + idOne +"> foaf:gender \"female\".\r\n"
				+ "<http://localhost:8890/mcas/person#per/" + idOne +"> foaf:age 7.";

		// System.out.println(rdfFlux);

		 KGraphManager.insertIntoKG(rdfFlux);

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
