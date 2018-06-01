package mcas.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.swrlapi.exceptions.LiteralException;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import com.google.common.base.Optional;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import mcas.Ontology.OntologyManager;
import mcas.RulesManager.OwlRules;
import mcas.util.IOManager;
import mcas.DataTransformation.TestToRDF2;
import mcas.DataTransformation.ToRDF;

public class MainController {
	
	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";
	
	public static void main(String[] args) throws Exception {

		String rdfFlux = ToRDF.directData2RDF(root + "lib/", root + "transformation/", "mcas-model.rml");
		
		System.out.println(rdfFlux);

//
//	public static OWLOntology domainOntology = OntologyManager
//			.loadOntologyFromInternet("http://purl.org/m-context/ontologies/mContext");
//
//	public static void main(String[] args) {
//
//		// // ---------- CREATE STREAM ------------
//
//		String rdfFlux = ToRDF.data2RDF(root + "transformation/", "mcas-model.rml", "salida.ttl");
//
//		System.out.println(rdfFlux);
//
//		// // --------------------------------------
//
//		// // ------ TO ONTOLOGY ---------------- //
//
//		// ----- LOAD FROM INTERNET
//		// OWLOntology domainOntology = OntologyManager
//		// .loadOntologyFromInternet("http://purl.org/m-context/ontologies/mContext");
//
//		// // ---- LOAD FROM PC FILE
//		// OWLOntology ontology = OntologyManager.loadOntology(root,
//		// "transformation/inputModified.rdf");
//
//		// // ----- LOAD FROM STREAM
//		OWLOntology ontology = OntologyManager.loadOntology(rdfFlux);
//
//		OntologyManager.getOntologyInfo(ontology);
//		OntologyManager.getOntologyInfo(domainOntology);
//		// OntologyManager.saveOntology(root, "salidaFlujo.ttl", ontology);
//
//		// // ---------------------------------
//
//		// ------- NOTA --------
//		// Puedo usar la merge o puedo usar solo el flujo de datos. De cualquier forma
//		// debo tratar de tener la clase representada en algún lugar.
//		// ---------------------
//
//		// OWLOntology ontology = OntologyManager.loadOntology(root,
//		// "ont/contextSWRL.owl");
//
//		// CREO QUE YA PUEDO DEJAR SOLO UNA FUNCIÓN
//		// OWLOntology rdfInput = OntologyManager.loadStream(root,
//		// "rdf/rdfInputPersonas.rdf");
//
//		// OWLOntology mergedOntology = OntologyManager.mergeOntologiesInManager();
//		//
//		// OntologyManager.getOntologyInfo(mergedOntology);
//
//		// // ------------- RULES ----------------
//
//		// OwlRules.getRulesList(ontology, root + "ont/" , "rulesNewVers.json");
//
//		// OntologyManager.saveOntology(root, "ont/salidaNuevoSujeto.owl", ontology);
//
//		// mcas.RulesManager.DroolsRules.executeDroolsRules();
//
//		System.out.println("Finalizado");

	}
}
