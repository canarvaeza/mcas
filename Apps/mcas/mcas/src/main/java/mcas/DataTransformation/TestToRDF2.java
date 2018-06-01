package mcas.DataTransformation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

import be.ugent.rml.DataFetcher;
import be.ugent.rml.Executor;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.RDF4JStore;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.functions.lib.*;

public class TestToRDF2 {
	
	public static void test () throws RDFParseException, UnsupportedRDFormatException, IOException {
		
		boolean removeDuplicates = false; //set to true if you want to remove duplicates triples/quads from the output
		String cwd = "C:\\Users\\dev\\Downloads\\wetransfer-beb93b\\"; //path to default directory for local files
		String mappingFile = "C:\\Users\\dev\\Downloads\\wetransfer-beb93b\\mapping.rml.ttl"; //path to the mapping file that needs to be executed
		List<String> triplesMaps = new ArrayList<>(); //list of triplesmaps to execute. When this list is empty all triplesmaps in the mapping file are executed
		
		InputStream mappingStream = new FileInputStream(mappingFile);
		Model model = Rio.parse(mappingStream, "", RDFFormat.TURTLE);
		RDF4JStore rmlStore = new RDF4JStore(model);
		
		Executor executor = new Executor(rmlStore, new RecordsFactory(new DataFetcher(cwd, rmlStore)));
		QuadStore result = executor.execute(triplesMaps, removeDuplicates);
		
		System.out.println(result.toSortedString());
		
	}
	
}
