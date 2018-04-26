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
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.LiteralException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

import com.jayway.jsonpath.JsonPath;

import mcas.Ontology.OntologyManager;

public class OwlRules {
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
			int indivIndex = 1;

			while (reglas.hasNext()) {

				Map<String, Object> thing = (Map<String, Object>) reglas.next();
				// String type = JsonPath.parse(thing).read("$.Type");

				String conclution = JsonPath.parse(thing).read("$.Conclution");
				SQWRLResult result = executeSQWRLRules(ontology, JsonPath.parse(thing).read("$.Name").toString(),
						JsonPath.parse(thing).read("$.Predicate").toString(),
						JsonPath.parse(thing).read("$.Conclution").toString());

				System.out.println(result);

				System.out.println(indivIndex);

				while (result.next()) {

					if (JsonPath.parse(thing).read("$.Type").equals("Unique")) {

						if (result.getLiteral("NumberLLCActivities").getInt() == 1) {

							String invidivualIRI = result.getNamedIndividual("Person").getPrefixedName();
							String activityName = "h" + Integer.toString(indivIndex++);

							ontology = OntologyManager.createIndividualInClass(ontology,
									JsonPath.parse(thing).read("$.Name").toString(), activityName);

							conclution = conclution.replace("-*ReplaceActivityIndividual*-", activityName);
							conclution = conclution.replace("-*ReplacePersonIndividual*-", invidivualIRI);

							System.out.println(conclution);

							ontology = executeSWRLRules(ontology,
									JsonPath.parse(thing).read("$.Name").toString() + "IndividualCreator", conclution);
						}

					} else {

						String invidivualIRI = result.getNamedIndividual("Person").getPrefixedName();
						String activityName = "h" + Integer.toString(indivIndex++);

						ontology = OntologyManager.createIndividualInClass(ontology,
								JsonPath.parse(thing).read("$.Name").toString(), activityName);

						conclution = conclution.replace("-*ReplaceActivityIndividual*-", activityName);
						conclution = conclution.replace("-*ReplacePersonIndividual*-", invidivualIRI);

						System.out.println(conclution);

						ontology = executeSWRLRules(ontology,
								JsonPath.parse(thing).read("$.Name").toString() + "IndividualCreator", conclution);

					}
					// OntologyManager.saveOntology(root, "ont/salidaSujetoPrueba.owl", ontology);
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

	public static OWLOntology executeSWRLRules(OWLOntology ontology, String ruleName, String ruleContent) {

		// Create OWLOntology instance using the OWLAPI
		OWLOntologyManager ontologyManager = ontology.getOWLOntologyManager();

		// Create a SWRL rule engine using the SWRLAPI
		SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

		try {
			SWRLAPIRule rule = ruleEngine.createSWRLRule(ruleName, ruleContent);

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

		} catch (SWRLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SWRLBuiltInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ontology;
	}

	public static SQWRLResult executeSQWRLRules(OWLOntology ontology, String queryName, String queryPredicate,
			String queryConclution) {
		SQWRLResult result = null;

		try {

			// Create SQWRL query engine using the SWRLAPI
			SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

			// Create and execute a SQWRL query
			result = queryEngine.runSQWRLQuery(queryName, queryPredicate);
		} catch (SQWRLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SWRLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(result);
		return result;
	}

}
