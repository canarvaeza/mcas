package mcas.Controller;

import virtuoso.jena.driver.VirtGraph;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import mcas.DataTransformation.ToRDF;
import mcas.DataTransformation.TransformationTemplates;
import mcas.KGraph.*;
import mcas.util.DatesManager;
import mcas.util.Initializer;

public class MainController {

	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";
	private static final String PROJECT_PATH = System.getProperty("user.dir");
	private static final String RESOURCES_PATH = Paths.get(PROJECT_PATH, "src/main/resources/").toString();
	private static final String TEST_RESOURCES_PATH = Paths.get(PROJECT_PATH, "src/test/resources/").toString();

	public static void main(String[] args) throws Exception {

		VirtGraph graph = new VirtGraph(QueryConf.connectionString, QueryConf.userName, QueryConf.passWord);

//		String[] files = {"input0.json","input1.json","input2.json","input3.json", "input4.json", "Sensors.json", "Locations.json", "Persons.json"}; // long
//		String[] files = { "test0.json", "Sensors.json", "Locations.json", "Persons.json" }; // long
//
//		Initializer initializer = new Initializer();
//		initializer.setNewDataFiles(files);
//		initializer.initialize(true, true, graph, TEST_RESOURCES_PATH);

		// String rdfFlux = "";

		// generate random UUIDs
//		UUID idOne = UUID.randomUUID();
//		UUID idTwo = UUID.randomUUID();
//		System.out.println("UUID One: " + idOne);
//		System.out.println("UUID Two: " + idTwo);

		String date = "2017-06-06";
		List<String> rules = KGraphManager.getRules(graph, date);
		String date_before = DatesManager.getNewDate(date, -1);
//		System.out.println(rules);
		Queries.constructNewRule(graph, "", rules, date, date_before);

	}
}
