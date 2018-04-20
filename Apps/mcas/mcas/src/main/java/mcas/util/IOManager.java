package mcas.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IOManager {

	static String root = "C:/Users/dev/Documents/GitHub/mcas/Apps/mcas/mcas/src/main/resources/";

	// TODO Json path https://github.com/json-path/JsonPath

	// public static void main(String[] args) {
	// JSONObject Json = null;
	// try {
	//
	// List<String> rules = getRules("json/rules.json", "SWRL", "Stable");
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public static List<String> getRules(String dir, String value, String status) {

		JSONObject Json;
		List<String> rules = null;
		try {
			Json = readJSON(dir);

			rules = new ArrayList<String>();

			Map<String, Object> JsonMap = jsonToMap(Json);
			JsonMap = (Map<String, Object>) JsonMap.get("mcontext");
			List<Map> rList = (List<Map>) JsonMap.get("Rules");

			Iterator<Map> jsonIterator = rList.iterator();

			while (jsonIterator.hasNext()) {

				Map<String, Object> it = jsonIterator.next();
				if (it.containsValue(value) && it.containsValue(status)) {
					// System.out.println(it.get("Content_Rules"));
					rules.add(it.get("Content_Rules").toString());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rules;
	}

	public static JSONObject readJSON(String dir) throws Exception {
		File file = new File(root + dir);
		String content = FileUtils.readFileToString(file, "utf-8");

		// Convert JSON string to JSONObject
		JSONObject Json = new JSONObject(content);

		return Json;
	}

	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		if (json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

}
