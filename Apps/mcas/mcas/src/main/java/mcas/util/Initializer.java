package mcas.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import mcas.DataTransformation.ToRDF;
import mcas.DataTransformation.TransformationTemplates;
import mcas.KGraph.KGraphManager;
import mcas.KGraph.Rules;
import virtuoso.jena.driver.VirtGraph;

public class Initializer{

	public static void initialize(boolean create_new_data, boolean create_rules,VirtGraph graph, String RESOURCES_PATH) {
		if (create_rules) {
			System.out.println("Creating new Rules");
			Rules.insert_all_rules_sparql(graph, Paths.get(RESOURCES_PATH, "rules", "rules.csv").toString());
		}

		if(create_new_data) {

			System.out.println("Creating flux data in virtuoso");
			String[] files = {"input0.json","input1.json","input2.json","input3.json"};
			for (String file: files) {
				try {
					String mapping_template = TransformationTemplates.transformTemplates.get("mcas");
					mapping_template = mapping_template.replace("<*input_json_file*>", Paths.get(RESOURCES_PATH,"transformation", file).toString());
					InputStream mappingFile = org.apache.commons.io.IOUtils.toInputStream(mapping_template, StandardCharsets.UTF_8);
					String rdfFlux = ToRDF.data2RDF(Paths.get(RESOURCES_PATH,"transformation").toString(), "mcas-model-03.rml.ttl", mappingFile, "salida.ttl");
					//					QueryConf.queryGraphBase + "person#" first If I want to use specific graph
					KGraphManager.insertConnectedData(graph, rdfFlux);
				} catch (Exception e) {
					System.out.println("\n\n\n\n---\nFILE " + file + " NOT PROCESSED\n----\n" + e);
					System.out.println("\n\n\n");
				}
			}
		}
	}
}
