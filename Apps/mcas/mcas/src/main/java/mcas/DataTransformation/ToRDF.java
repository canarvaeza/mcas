package mcas.DataTransformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import javax.rmi.CORBA.UtilDelegate;

/*
 * RML VERSION 3.0 

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

*/

// ----- RML NEW VERSION MAY 2018 -------

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

import be.ugent.rml.DataFetcher;
import be.ugent.rml.Executor;
import be.ugent.rml.Utils;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.RDF4JStore;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.functions.lib.*;

import mcas.util.IOManager;

public class ToRDF {
	
	public static String data2RDF(String root, String mapping_name, String outputFile_name) {
		
		String rdfFlux = null;
		
		boolean removeDuplicates = false; //set to true if you want to remove duplicates triples/quads from the output
		String cwd = root; //path to default directory for local files
		String mappingFile = Paths.get(root, mapping_name).toString(); //path to the mapping file that needs to be executed
		List<String> triplesMaps = new ArrayList<String>(); //list of triplesmaps to execute. When this list is empty all triplesmaps in the mapping file are executed
		
		InputStream mappingStream;
		try {
			mappingStream = new FileInputStream(mappingFile);
			Model model = Rio.parse(mappingStream, "", RDFFormat.TURTLE);
			RDF4JStore rmlStore = new RDF4JStore(model);
			
			Executor executor = new Executor(rmlStore, new RecordsFactory(new DataFetcher(cwd, rmlStore)));
			QuadStore result = executor.execute(triplesMaps, removeDuplicates);
			
			rdfFlux = Utils.toNTriples(result.getQuads("", "", ""));
			rdfFlux = rdfFlux.replace("%2F", "/").replace("%23", "#"); // Because is coming without format
			//rdfFlux = rdfFlux.replaceAll("\\^\\^(.*)\\.", "^^<$1>."); // Because is not a prefix
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RDFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedRDFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rdfFlux;
	}

	public static String directData2RDF(String jarDir, String mappingDir, String mappingName) throws Exception {
//		String jarDir = "src\\main\\resources\\transformation\\wetransfer-beb93b\\";
//		String mappingDir = "src\\main\\resources\\transformation\\wetransfer-beb93b\\";
//		String mappingName = "mapping.rml.ttl";
		
		Process ps=Runtime.getRuntime().exec(new String[]{"java","-jar",jarDir + "rmlmapper-1.0.0.jar","-m", mappingDir + mappingName});//, "-p", "direction=" + fileDir});
		ps.waitFor();
        java.io.InputStream is=ps.getInputStream();
        
        byte b[]=new byte[is.available()];
        is.read(b,0,b.length);
        
//        System.out.println(new String(b));    
        //String content = new String(b);
//        List<String> prefixes = getTransformPrefixes(mappingDir, mappingName);
//        System.out.println(prefixes.toString());
        return new String(b);
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
	
	
//	public static String data2RDF(String root, String mapping_name, String outputFile_name) {
//		String rdfFlux = null;
//		try {
//			File mapping_file = Paths.get(root + mapping_name).toFile();
//			File outputFile = Paths.get(root + outputFile_name).toFile();
//			List<String> prefixes = getTransformPrefixes(root, mapping_name);
//
////			//TRYING TO use prefixes dinamically to the rr:template
//			String parametersString = "direction=" + root; // + ",foaf=xmlns.com/foaf/0.1/";
//
////			for (String s : prefixes) {
////				parametersString += "," + s.replace("http://", "").replace(",", "=");
////			}
//
//			java.util.Map<String, String> parameters = retrieveParameters(parametersString);
//			String graphName = "";
//			String[] exeTriplesMap = null;
//			String outputFormat = "turtle";
//
//			RMLDocRetrieval mapDocRetrieval = new RMLDocRetrieval();
//			final Repository repository = mapDocRetrieval.getMappingDoc(mapping_file.toString(), RDFFormat.TURTLE);
//
//			// check mapping
//			if (repository == null) {
//				System.err.println("Problem retrieving the RML Mapping Document");
//				System.exit(1);
//			}
//
//			StdRMLMappingFactory mappingFactory = new StdRMLMappingFactory();
//
//			System.out.println("========================================");
//			System.out.println("Extracting the RML Mapping Definitions..");
//			System.out.println("========================================");
//
//			RMLMapping mapping = mappingFactory.extractRMLMapping(repository);
//			RMLEngine engine = new StdRMLEngine(outputFile.toString());
//			
//			// If you want output file
//			// RMLDataset runningDataset = engine.chooseSesameDataSet("dataset", outputFile.toString(), outputFormat);
//			
//			RMLDataset runningDataset = engine.chooseSesameDataSet("dataset", null, outputFormat);
//			
//			runningDataset = createNewPrefixes(runningDataset, prefixes);
//			
//			runningDataset = engine.runRMLMapping(runningDataset, mapping, graphName, parameters, exeTriplesMap);
//
//			Thread.sleep(2000);
//
//			System.out.println("========================================");
//			System.out.println("Transformation Completed");
//			System.out.println("========================================");
//			
//			//-------- GET THE OUTPUT CONTENT -------
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			PrintStream ps = new PrintStream(baos, true, "utf-8");
//			runningDataset.dumpRDF(ps, RDFFormat.TURTLE);
//			rdfFlux = new String(baos.toByteArray(), StandardCharsets.UTF_8);
//			ps.close();
//			// -----------------------
//			
//			//WITH THIS THE EXECUTION TAKES TOO MUCH
////			runningDataset.closeRepository();
//			
//			rdfFlux = rdfFlux.replace("%2F", "/").replace("%23", "#");
//			
//			return rdfFlux;
//
//		} catch (Throwable e) {
//			System.err.println("\n\n\n\nSOMETHING WENT WRONG!");
//			e.printStackTrace(System.err);
//		}
//		return rdfFlux;
//
//	}
//
//	public static boolean searchNameSpace(RepositoryResult<Namespace> arr, String targetValue) {
//		while(arr.hasNext()) {
//			Namespace ns = arr.next();
//			if(ns.getPrefix().equals(targetValue)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	
//	public static RMLDataset createNewPrefixes(RMLDataset runningDataset, List<String> prefixes) {
//
//		try {
//			RepositoryResult<Namespace> nameSpaces = runningDataset.getRepository().getConnection().getNamespaces();
//			
//			for (int n = 0; n < prefixes.size(); n++) {
//				if (searchNameSpace(nameSpaces, prefixes.get(n).split(",")[0]) == false) {
//					runningDataset.getRepository().getConnection().setNamespace(prefixes.get(n).split(",")[0],
//							prefixes.get(n).split(",")[1]);
//					System.out.println("Created new Namespace: " + prefixes.get(n));
//				}
//			}
//
//		} catch (RepositoryException ex) {
//			Logger.getLogger(TriplesMap.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		return runningDataset;
//	}

}
