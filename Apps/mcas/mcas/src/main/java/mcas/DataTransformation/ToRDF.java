package mcas.DataTransformation;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.openrdf.repository.Repository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;

import be.ugent.mmlab.rml.core.RMLEngine;
import be.ugent.mmlab.rml.core.StdRMLEngine;
import be.ugent.mmlab.rml.mapdochandler.extraction.std.StdRMLMappingFactory;
import be.ugent.mmlab.rml.mapdochandler.retrieval.RMLDocRetrieval;
import be.ugent.mmlab.rml.model.RMLMapping;
import be.ugent.mmlab.rml.model.dataset.RMLDataset;

public class ToRDF {

	public static void data2RDF(String root, String mapping_name, String outputFile_name) {

		try {
			// TODO: delete repositories folder

//			String root = "C:/Users/dev/Desktop/transformar/";

			File mapping_file = Paths.get(root + mapping_name).toFile();
			
			System.out.println(mapping_file.toString());

			File outputFile = Paths.get(root + outputFile_name).toFile();

			java.util.Map<String, String> parameters = retrieveParameters("direction=" + root);

			RMLDocRetrieval mapDocRetrieval = new RMLDocRetrieval();
			final Repository repository = mapDocRetrieval.getMappingDoc(mapping_file.toString(), RDFFormat.TURTLE);

			// check mapping
			if (repository == null) {
				System.err.println("Problem retrieving the RML Mapping Document");
				System.exit(1);
			}

			StdRMLMappingFactory mappingFactory = new StdRMLMappingFactory();

			System.out.println("========================================");
			System.out.println("Extracting the RML Mapping Definitions..");
			System.out.println("========================================");
			RMLMapping mapping = mappingFactory.extractRMLMapping(repository);

			String graphName = "";

			String[] exeTriplesMap = null;

			String outputFormat = Rio.getWriterFormatForFileName(outputFile.toString()).getName().toLowerCase();

			RMLEngine engine = new StdRMLEngine(outputFile.toString());

			// exploded method: engine.run(...);

			final RMLDataset runningDataset = engine.chooseSesameDataSet("dataset", outputFile.toString(),
					outputFormat);
			engine.runRMLMapping(runningDataset, mapping, graphName, parameters, exeTriplesMap);

			// close the repository
			runningDataset.closeRepository();

			Thread.sleep(2000);
			
			System.out.println("========================================");
			System.out.println("Transformation Completed");
			System.out.println("========================================");
			
			
			System.exit(0);

		} catch (Throwable e) {
			System.err.println("\n\n\n\nSOMETHING WENT WRONG!");
			e.printStackTrace(System.err);
		}
	}

	public static Map<String, String> retrieveParameters(String parameter) {
		Map<String, String> parameters = new HashMap<String, String>();
		String[] parameterKeyValue;

		String[] subParameters = parameter.split(",");
		for (String subParameter : subParameters) {
			parameterKeyValue = subParameter.split("=");

			String key = parameterKeyValue[0];
			String value = parameterKeyValue[1];

			parameters.put(key, value);
		}

		return parameters;
	}

}