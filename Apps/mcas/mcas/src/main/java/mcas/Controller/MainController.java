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
import mcas.DataTransformation.ToRDF;

public class MainController {

	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";

	public static void main(String[] args) {
			
		
//		String rdfFlux = ToRDF.data2RDF(root + "transformation/", "mcas-model.rml", "salida.ttl");
		
		String rdfFlux = "@prefix mcontext: <http://www.semanticweb.org/ontologies/mcontext#> .\r\n" + 
				"@prefix alzheimer: <http://www.semanticweb.org/ontologies/domains/alzheimer#> .\r\n" + 
				"@prefix role: <http://www.semanticweb.org/ontologies/role#> .\r\n" + 
				"@prefix foaf: <http://xmlns.com/foaf/0.1/> .\r\n" + 
				"@prefix sosa: <http://www.w3.org/ns/sosa/> .\r\n" + 
				"@prefix wgs84_pos: <http://www.w3.org/2003/01/geo/wgs84_pos#> .\r\n" + 
				"@prefix schema: <http://schema.org/> .\r\n" + 
				"@prefix gn: <http://www.geonames.org/ontology#> .\r\n" + 
				"@prefix location: <http://www.semanticweb.org/ontologies/location#> .\r\n" + 
				"@prefix multimedia: <http://www.semanticweb.org/ontologies/multimedia#> .\r\n" + 
				"@prefix ma: <http://www.w3.org/ns/ma-ont#> .\r\n" + 
				"@prefix activity: <http://www.semanticweb.org/ontologies/activity#> .\r\n" + 
				"@prefix time: <http://www.semanticweb.org/ontologies/time> .\r\n" + 
				"\r\n" + 
				"<http://xmlns.com/foaf/0.1/User/u001> a foaf:Person ;\r\n" + 
				"	foaf:lastname \"Narvaez\" ;\r\n" + 
				"	foaf:firstname \"Cristian\" ;\r\n" + 
				"	mcontext:hasRol <http://www.semanticweb.org/ontologies/domains/alzheimer#> .\r\n" + 
				"\r\n" + 
				"<http://www.w3.org/ns/sosa/iphone7/35-207306-844818-0> a sosa:Platform ;\r\n" + 
				"	sosa:hosts <http://www.w3.org/ns/sosa/sensor/35-207306-844818-0/BMP282> ;\r\n" + 
				"	gn:locatedIn <http://www.semanticweb.org/ontologies/location#Location/0001> .\r\n" + 
				"\r\n" + 
				"<http://www.w3.org/ns/sosa/sensor/35-207306-844818-0/BMP282> a sosa:Sensor ;\r\n" + 
				"	sosa:observes <http://www.w3.org/ns/sosa/sensor/35-207306-844818-0/BMP282/Gyroscope> .\r\n" + 
				"\r\n" + 
				"<http://www.w3.org/ns/sosa/Observation/001> a sosa:Observation ;\r\n" + 
				"	sosa:madeBySensor <http://www.w3.org/ns/sosa/sensor/35-207306-844818-0/BMP282> ;\r\n" + 
				"	mcontext:describe <http://www.semanticweb.org/ontologies/activity#activity/low/00002> ;\r\n" + 
				"	sosa:hasSimpleResult \"[0.60034, 0.42556, 0.86662]\"^^<http://www.w3.org/2001/XMLSchema#decimal> ;\r\n" + 
				"	sosa:resultTime \"2017-06-06T12:36:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> .\r\n" + 
				"\r\n" + 
				"<http://www.semanticweb.org/ontologies/activity#activity/low/00001> a <http://www.semanticweb.org/ontologies/domains/alzheimer#Eat> ;\r\n" + 
				"	<http://www.semanticweb.org/ontologies/timehasBeginningTime> \"2017-06-06T12:36:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> ;\r\n" + 
				"	<http://www.semanticweb.org/ontologies/timehasEndingTime> \"2017-06-06T12:38:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> .\r\n" + 
				"\r\n" + 
				"<http://www.semanticweb.org/ontologies/activity#activity/low/00002> a <http://www.semanticweb.org/ontologies/domains/alzheimer#Sit> ;\r\n" + 
				"	<http://www.semanticweb.org/ontologies/timehasBeginningTime> \"2017-06-06T12:36:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> .\r\n" + 
				"\r\n" + 
				"<http://www.semanticweb.org/ontologies/location#Location/0001> wgs84_pos:lat \"40.414\" ;\r\n" + 
				"	a <http://www.semanticweb.org/ontologies/location#ClosedLocation> ;\r\n" + 
				"	wgs84_pos:long \"-3.699\" .\r\n" + 
				"\r\n" + 
				"<http://www.semanticweb.org/ontologies/multimedia#multimedia/0001> mcontext:describe <http://www.semanticweb.org/ontologies/activity#activity/low/00001> ;\r\n" + 
				"	multimedia:creationDate \"2017-06-06T12:36:12Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> ;\r\n" + 
				"	ma:hasFormat <http://AVI> ;\r\n" + 
				"	a <http://www.semanticweb.org/ontologies/multimedia#VideoFormat> ;\r\n" + 
				"	gn:locatedIn <http://www.semanticweb.org/ontologies/location#Location/0001> .";
		
		
		System.out.println(rdfFlux);
		
		InputStream is = new ByteArrayInputStream(rdfFlux.getBytes(StandardCharsets.UTF_8));
		OWLOntology ontology = OntologyManager.loadOntology(is);
		OntologyManager.getOntologyInfo(ontology);
		OntologyManager.saveOntology(root, "salidaFlujo.ttl", ontology);
		
//		OWLOntology ontology = OntologyManager.loadOntology(root, "transformation/salida.ttl");
		
		
//		OntologyManager.getOntologyInfo(ontology);

		
		// ------- NOTA --------
		// Puedo usar la merge o puedo usar solo el flujo de datos. De cualquier forma
		// debo tratar de tener la clase representada en algún lugar.
		// ---------------------

//		OWLOntology ontology = OntologyManager.loadOntology(root, "ont/contextSWRL.owl");

		// CREO QUE YA PUEDO DEJAR SOLO UNA FUNCIÓN
		// OWLOntology rdfInput = OntologyManager.loadStream(root,
		// "rdf/rdfInputPersonas.rdf");

		// OWLOntology mergedOntology = OntologyManager.mergeOntologiesInManager();
		//
		// OntologyManager.getOntologyInfo(mergedOntology);
		
		
		
//		 RulesValidator.getRulesList(ontology, root);


//		OntologyManager.saveOntology(root, "ont/salidaNuevoSujeto.owl", ontology);
		
//		mcas.RulesManager.DroolsRules.executeDroolsRules();
		
		

		System.out.println("Finalizado");
		

	}
}
