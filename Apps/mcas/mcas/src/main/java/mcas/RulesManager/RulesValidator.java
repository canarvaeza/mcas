package mcas.RulesManager;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.LiteralException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import com.jayway.jsonpath.JsonPath;

public class RulesValidator {
	// public static void main(String[] args) {

	public static String getRulesList(OWLOntology ontology, String root) {

		String contenido = null;

		File file = new File(root + "ont/rules.json");
		String content;
		try {
			content = FileUtils.readFileToString(file, "utf-8");

			// String jsonContext =
			// JsonPath.parse(content).read("$.mcontext.Rules.[0].Name");
			List<JsonPath> lists = JsonPath.parse(content).read("$.mcontext.Rules");

			// System.out.println(lists);

			Iterator<JsonPath> reglas = lists.iterator();

			while (reglas.hasNext()) {

				Map<String, Object> thing = (Map<String, Object>) reglas.next();
				// String type = JsonPath.parse(thing).read("$.Type");
				String conclution = JsonPath.parse(thing).read("$.Conclution");

				if (JsonPath.parse(thing). read("$.Type").equals("SQWRL")) {
					SQWRLResult result = executeSQWRLRules(ontology, JsonPath.parse(thing).read("$.Name").toString(),
							JsonPath.parse(thing).read("$.Predicate").toString(),
							JsonPath.parse(thing).read("$.Conclution").toString());

					if (result.getNumberOfRows() != 0) {
						System.out.println("entro a dif 0");
						
						// TERMINAR DE ESCRIBIR ESTA ´PARTE DE CÓDIGO
						
						
						for (int i = 0; i < result.getNumberOfRows(); i++) {
							System.out.println("Entro a ciclo");
							System.out.println(result.getValue(i, 0));

							if (result.getValue(i, 1).equals("1")) { // CORREGIR
								System.out.println("Entro a igual a 1");
								conclution = conclution.replace("-*ReplaceActivityIndividual*-",
										JsonPath.parse(thing).read("$.Name").toString());
								conclution = conclution.replace("-*ReplacePersonIndividual*-", result.getValue(i, 0).toString());

								System.out.println(conclution);

							}

						}

					}

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LiteralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQWRLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contenido;
	}

	public static void executeSWRLRules(String root) {
		try {
			// Create OWLOntology instance using the OWLAPI
			OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology;
			ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(root + "ont/contextSWRL.owl"));

			String ontologyBase = ontology.getOntologyID().getOntologyIRI().get().toString();
			// Create a SWRL rule engine using the SWRLAPI
			SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

			// GET SOME ONTOLOGY INFO

			// System.out.println(ontology.getClassesInSignature());
			// System.out.println(ontology.getDataPropertiesInSignature());
			// System.out.println(ontology.getOntologyID().getOntologyIRI().get());
			// System.out.println(ontologyManager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat().getDefaultPrefix());

			// CHANGE THE PREFIX
			// ontologyManager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat().setDefaultPrefix(ontologyBase
			// + "#");
			// System.out.println(ontologyManager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat().getDefaultPrefix());

			// NO LA PONGO PORQUE ME ESTÁ CREANDO LAS REGLAS EN LA ONTOLOGÍA, NO SOLO LAS
			// EVALÚA
			// SWRLAPIRule rule = ruleEngine.createSWRLRule("HavingMeal",
			// "contextswrl:Person(?p) ^ contextswrl:Sit(?A1) ^ contextswrl:Eat(?A2) ^
			// contextswrl:isInvolvedIn(?p, ?A1) ^ contextswrl:isInvolvedIn(?p, ?A2) ^
			// contextswrl:HighContextActivity(contextswrl:hca2) ->
			// contextswrl:HavingMeal(contextswrl:hca2) ^ contextswrl:isInvolvedIn(?p,
			// contextswrl:hca2)");
			//
			// rule = ruleEngine.createSWRLRule("Rest",
			// "contextswrl:Person(?p) ^ contextswrl:Sit(?A1) ^ contextswrl:isInvolvedIn(?p,
			// ?A1) ^ contextswrl:HighContextActivity(contextswrl:hca1) ->
			// contextswrl:Rest(contextswrl:hca1) ^ contextswrl:isInvolvedIn(?p,
			// contextswrl:hca1)");

			// Run the rule engine
			ruleEngine.infer();
			System.out.println("Finalizado");
			ontologyManager.saveOntology(ontology, IRI.create(new File(root + "/ont/OntR.owl")));

		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			System.err.println("Error creating OWL ontology: " + e.getMessage());
			System.exit(-1);
			e.printStackTrace();

			// }catch (SWRLParseException e) {
			// System.err.println("Error parsing SWRL rule or SQWRL query: " +
			// e.getMessage());
			// e.printStackTrace();
			// System.exit(-1);
			// } catch (SWRLBuiltInException e) {
			// System.err.println("Error building SWRL rule or SQWRL query: " +
			// e.getMessage());
			// System.exit(-1);
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static SQWRLResult executeSQWRLRules(OWLOntology ontology, String queryName, String queryPredicate,
			String queryConclution) {
		SQWRLResult result = null;

		try {

			// Create SQWRL query engine using the SWRLAPI
			SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

			// Create and execute a SQWRL query
			result = queryEngine.runSQWRLQuery(queryName, queryPredicate);

			System.out.println("Ended");

			// } catch (OWLOntologyCreationException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQWRLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SWRLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(result);
		return result;
	}

}
