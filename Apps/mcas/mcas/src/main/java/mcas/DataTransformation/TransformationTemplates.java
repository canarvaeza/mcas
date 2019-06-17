package mcas.DataTransformation;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import mcas.util.IOManager;


public class TransformationTemplates {
	
	private static final String PROJECT_PATH = System.getProperty("user.dir");
	private static final String RESOURCES_PATH = Paths.get(PROJECT_PATH,"src/main/resources/").toString();
	
	final static String mcas_template = IOManager.loadFile(Paths.get(RESOURCES_PATH, "transformation","mcas-model-template.rml.ttl").toString());
	
	public static final Map<String, String> transformTemplates = new HashMap<String, String>(){
		{
			put("mcas", mcas_template);
		}
	};
}
