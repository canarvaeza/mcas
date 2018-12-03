package mcas.Controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import mcas.KGraph.KGraphManager;
import mcas.KGraph.Queries;
import mcas.KGraph.QueryConf;
import mcas.util.DatesManager;
import mcas.util.Initializer;
import virtuoso.jena.driver.VirtGraph;

public class MainController {

	public static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";
	private static final String PROJECT_PATH = System.getProperty("user.dir");
	private static final String RESOURCES_PATH = Paths.get(PROJECT_PATH, "src/main/resources/").toString();
	private static final String TEST_RESOURCES_PATH = Paths.get(PROJECT_PATH, "src/test/resources/").toString();

	public static Logger newLogger(String name) throws SecurityException, IOException {
		Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		Handler fileHandler = new FileHandler("./" + name + ".log");
		fileHandler.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fileHandler);
		return LOGGER;
	}

	public static void main(String[] args) throws Exception {
		Logger logg_one_insert = newLogger("one_insert_logger");
		Logger logg_multiple_insert = newLogger("multiple_insert_logger");

		VirtGraph graph = new VirtGraph(QueryConf.connectionString, QueryConf.userName, QueryConf.passWord);

		String[] files = { "Sensors.json", "Locations.json", "Persons.json", "Multimedia.json", "test0.json", "test1.json", "test2.json", "test3.json", "test4.json", "test5.json", "test6.json","test7.json", "test8.json", "test9.json", "test10.json", "test11.json", "test12.json" }; // long
//		String[] files = {"Multimedia.json","test0.json", "test1.json", "test2.json",	"test3.json", "test4.json", "test5.json", "test6.json","test7.json", "test8.json", "test9.json", "test10.json", "test11.json", "test12.json"}; // long
//		String[] files = { "Sensors.json", "Locations.json", "Persons.json", "Multimedia.json", "test10.json","test11.json"};

		Initializer initializer = new Initializer();
		initializer.setNewDataFiles(files);
		initializer.initialize(false, true, graph, TEST_RESOURCES_PATH);
//		logg_multiple_insert.info("create multiple 100 times 700 data each");
//		for (int j = 0; j < 100; j++) {
//			Instant start = Instant.now();
//			for (int i = 0; i < 100; i++) {
//				initializer.initialize(false, true, graph, TEST_RESOURCES_PATH);
//			}
//			Instant finish = Instant.now();
//			long timeElapsed = Duration.between(start, finish).toNanos();
//			logg_multiple_insert.info(Long.toString(timeElapsed));
//		}

//		initializer.initialize(false, true, graph, TEST_RESOURCES_PATH);

		// String rdfFlux = "";

		// generate random UUIDs
//		UUID idOne = UUID.randomUUID();
//		UUID idTwo = UUID.randomUUID();
//		System.out.println("UUID One: " + idOne);
//		System.out.println("UUID Two: " + idTwo);

		String date = "2018-10-06";
		List<String> rules = KGraphManager.getRules(graph, date);
		String date_before = DatesManager.getNewDate(date, -1);
//		System.out.println(rules);
		Queries.constructNewRule(graph, "", rules, date, date_before);

	}
}
