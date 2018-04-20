package mcas.Controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.swrlapi.exceptions.LiteralException;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import mcas.Ontology.OntologyManager;
//import mcas.RulesManager.RulesValidator;
import mcas.RulesManager.RulesValidator;

public class MainController {

	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";

	public static void main(String[] args) {
		
//		------- NOTA --------
//		Puedo usar la merge o puedo usar solo el flujo de datos. De cualquier forma debo tratar de tener la clase representada en algún lugar.
//		---------------------
		
		OWLOntology ontology = OntologyManager.loadOntology(root, "ont/contextSWRL.owl");
		
//		CREO QUE YA PUEDO DEJAR SOLO UNA FUNCIÓN
//		OWLOntology rdfInput = OntologyManager.loadStream(root, "rdf/rdfInputPersonas.rdf");

//		OWLOntology mergedOntology = OntologyManager.mergeOntologiesInManager();
//		
//		OntologyManager.getOntologyInfo(mergedOntology);
//		
		
//		SQWRLResult result = RulesValidator.executeSQWRLRules(ontology,"Rest" ,"contextswrl:Person(?person) ^ contextswrl:Sit(?A1) ^ contextswrl:isInvolvedIn(?person, ?A1) ^ contextswrl:LowContextActivity(?llca) ^ contextswrl:isInvolvedIn(?person, ?llca) -> sqwrl:select(?person) ^ sqwrl:count(?llca) ^ sqwrl:columnNames(\"Person\", \"NumberLLCActivities\")" ,"contextswrl:Rest(-*ReplaceActivityIndividual*-) ^ contextswrl:isInvolvedIn(-*ReplacePersonIndividual*-, -*ReplaceActivityIndividual*-)");
		
		RulesValidator.getRulesList(ontology, root);
		
		
	}
}
