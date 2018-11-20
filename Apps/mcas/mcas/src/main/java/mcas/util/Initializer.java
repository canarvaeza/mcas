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
	
	public String[] files;
	
	public void setNewDataFiles (String[] files) {
		this.files = files;
	}
	
	public Initializer() {
		
	}
	
	/**
	 * Method that initialise the Virtuoso DB
	 * @param create_new_data -> If you wan to put data on the virtuoso DB
	 * @param create_rules -> If you want to create rules on the virtuoso DB
	 * @param graph -> The graph you want to change
	 * @param RESOURCES_PATH -> The Resources path, should have a transformation and rules folder
	 * @return boolean -> The service was successful
	 */
	public boolean initialize(boolean create_new_data, boolean create_rules,VirtGraph graph, String RESOURCES_PATH) {

		if(create_new_data) {

			System.out.println("Creating flux data in virtuoso");
			for (String file: this.files) {
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
		if (create_rules) {
			System.out.println("Creating new Rules");
			Rules.insert_all_rules_sparql(graph, Paths.get(RESOURCES_PATH, "rules", "rules.csv").toString());
		}
		return true;
	}
}
