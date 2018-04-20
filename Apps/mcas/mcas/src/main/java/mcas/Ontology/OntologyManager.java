package mcas.Ontology;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import mcas.RulesManager.RulesValidator;

public class OntologyManager {

	private static OWLOntologyManager mergedOntologyManager = OWLManager.createOWLOntologyManager();
	
	
	public static void getOntologyInfo(OWLOntology ontology) {

		System.out.println("-------------------------------------------------");
		System.out.println("Classes: " + ontology.getClassesInSignature());
		System.out.println("Object Properties: " + ontology.getObjectPropertiesInSignature());
		System.out.println("Data Properties: " + ontology.getDataPropertiesInSignature());
		System.out.println("Individuals: " + ontology.getIndividualsInSignature());
		System.out.println("-------------------------------------------------");
	}
	
	public static OWLOntologyManager getMergedManager() {
		System.out.println("Las ontologías presentes son: " + mergedOntologyManager.getOntologies().toString());
		return mergedOntologyManager;
	}

	/*public static void createContent(OWLOntology ontology) {

		SQWRLResult result = RulesValidator.executeSQWRLRules(ontology);

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();

		try {
			while (result.next()) {
				System.out.println(result.getColumnName(1) + ": " + result.getNamedIndividual(0).getIRI());
				System.out.println(result.getColumnName(1) + ": " + result.getLiteral(1).getInteger());
			}
		} catch (SQWRLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public static OWLOntology loadOntology(String root, String ontologyName) {
		SQWRLResult result = null;
		// Create OWLOntology instance using the OWLAPI
		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = null;
		try {
			ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(root + ontologyName));
			mergedOntologyManager.loadOntologyFromOntologyDocument(new File(root + ontologyName));

			ontologyManager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat()
					.setDefaultPrefix(ontology.getOntologyID().getOntologyIRI().get() + "#");
			// System.out.println(ontologyManager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat().getDefaultPrefix());
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ontology;
	}

	/*
	public static OWLOntology loadStream(String root, String ontologyName) {
		SQWRLResult result = null;
		// Create OWLOntology instance using the OWLAPI
		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = null;
		try {
			ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(root + ontologyName));
			mergedOntologyManager.loadOntologyFromOntologyDocument(new File(root + ontologyName));

			ontologyManager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat()
					.setDefaultPrefix(ontology.getOntologyID().getOntologyIRI().get() + "#");
//			System.out.println(
//					ontologyManager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat().getDefaultPrefix());
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ontology;
	}
	*/

	/*
	public static OWLOntology mergeOntologiesInManager() {

		OWLOntologyManager manager = OntologyManager.getMergedManager();

		OWLOntologyMerger merger = new OWLOntologyMerger(manager);

		// IRI outputPath = IRI.create(new File(outputPathString));
		IRI outputIRI = IRI.create("urn:absolute:www.merged.com/ont"); // ES LO QUE PIDEN

		OWLOntology merged = null;

		// merge
		try {
			merged = merger.createMergedOntology(manager, outputIRI);
		} catch (OWLOntologyCreationException e) {
			System.out.println("ERROR: Could not merge ontologies");
			System.out.println(e.getMessage());
		}

		return merged;
	}
	*/


}
