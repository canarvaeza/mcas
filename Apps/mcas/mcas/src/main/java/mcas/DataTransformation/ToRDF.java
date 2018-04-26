package mcas.DataTransformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.rio.RDFFormat;

import be.ugent.mmlab.rml.core.RMLEngine;
import be.ugent.mmlab.rml.core.StdRMLEngine;
import be.ugent.mmlab.rml.mapdochandler.extraction.std.StdRMLMappingFactory;
import be.ugent.mmlab.rml.mapdochandler.retrieval.RMLDocRetrieval;
import be.ugent.mmlab.rml.model.RMLMapping;
import be.ugent.mmlab.rml.model.TriplesMap;
import be.ugent.mmlab.rml.model.dataset.RMLDataset;
import mcas.util.IOManager;

public class ToRDF {

	public static String data2RDF(String root, String mapping_name, String outputFile_name) {
		String rdfFlux = null;
		try {
			File mapping_file = Paths.get(root + mapping_name).toFile();
			File outputFile = Paths.get(root + outputFile_name).toFile();
			List<String> prefixes = getTransformPrefixes(root, mapping_name);

//			//TRYING TO use prefixes dinamically to the rr:template
			String parametersString = "direction=" + root; // + ",foaf=xmlns.com/foaf/0.1/";

//			for (String s : prefixes) {
//				parametersString += "," + s.replace("http://", "").replace(",", "=");
//			}

			java.util.Map<String, String> parameters = retrieveParameters(parametersString);
			String graphName = "";
			String[] exeTriplesMap = null;
			String outputFormat = "turtle";

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
			RMLEngine engine = new StdRMLEngine(outputFile.toString());
			
			// If you want output file
			// RMLDataset runningDataset = engine.chooseSesameDataSet("dataset", outputFile.toString(), outputFormat);
			
			RMLDataset runningDataset = engine.chooseSesameDataSet("dataset", null, outputFormat);
			
			runningDataset = createNewPrefixes(runningDataset, prefixes);
			
			runningDataset = engine.runRMLMapping(runningDataset, mapping, graphName, parameters, exeTriplesMap);

			Thread.sleep(2000);

			System.out.println("========================================");
			System.out.println("Transformation Completed");
			System.out.println("========================================");
			
			//-------- GET THE OUTPUT CONTENT -------
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos, true, "utf-8");
			runningDataset.dumpRDF(ps, RDFFormat.TURTLE);
			rdfFlux = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			ps.close();
			// -----------------------
			
			//WITH THIS THE EXECUTION TAKES TOO MUCH
//			runningDataset.closeRepository();
			
			rdfFlux = rdfFlux.replace("%2F", "/").replace("%23", "#");
			
			return rdfFlux;

		} catch (Throwable e) {
			System.err.println("\n\n\n\nSOMETHING WENT WRONG!");
			e.printStackTrace(System.err);
		}
		return rdfFlux;

	}

	public static boolean searchNameSpace(RepositoryResult<Namespace> arr, String targetValue) {
		while(arr.hasNext()) {
			Namespace ns = arr.next();
			if(ns.getPrefix().equals(targetValue)) {
				return true;
			}
		}
		return false;
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

	
	public static RMLDataset createNewPrefixes(RMLDataset runningDataset, List<String> prefixes) {

		try {
			RepositoryResult<Namespace> nameSpaces = runningDataset.getRepository().getConnection().getNamespaces();
			
			for (int n = 0; n < prefixes.size(); n++) {
				if (searchNameSpace(nameSpaces, prefixes.get(n).split(",")[0]) == false) {
					runningDataset.getRepository().getConnection().setNamespace(prefixes.get(n).split(",")[0],
							prefixes.get(n).split(",")[1]);
					System.out.println("Created new Namespace: " + prefixes.get(n));
				}
			}

		} catch (RepositoryException ex) {
			Logger.getLogger(TriplesMap.class.getName()).log(Level.SEVERE, null, ex);
		}
		return runningDataset;
	}
	
	public static List<String> getTransformPrefixes(String root, String mapping_name) {

		String fileContent = IOManager.loadFile(root, mapping_name);

		List<String> prefixes = new ArrayList<String>();

		Scanner scan = new Scanner(fileContent);

		boolean dependencies = false;

		while (scan.hasNext()) {
			String line = scan.nextLine();

			String prefixName = null;
			String prefixDir = null;

			// Only dependencies after the dependencies annotation
			if (line.contains("###") && line.contains("DEPENDENCIES")) {
				dependencies = true;
			}
			// get all prefixes
			if (dependencies && line.contains("@prefix ")) {
				prefixName = line.split(": ")[0].split(" ")[1];
				prefixDir = line.split(": ")[1].substring(line.split(": ")[1].indexOf("<") + 1,
						line.split(": ")[1].indexOf(">"));
				// The regex: \<([^\[\]]*)\>
				prefixes.add(prefixName + "," + prefixDir);
			}
		}
		return prefixes;

	}

}
