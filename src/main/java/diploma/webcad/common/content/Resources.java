package diploma.webcad.common.content;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import diploma.webcad.common.util.collection.Pair;
import diploma.webcad.core.model.Language;

public class Resources {

	public static String APPLICATION_RESOURCE_BUNDLE_BASE = "locales/messages";

	public static Pair<Map<String,String>,List<String>> getLanguageResourses(Language language) throws IOException {
		HashMap<String, String> res = new HashMap<String, String>();
		LinkedList<String> err=new LinkedList<String>();
		InputStream in = language
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						APPLICATION_RESOURCE_BUNDLE_BASE + "_"
								+ language.getIso() + ".properties");
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line;
		int i=1;
		while ((line = br.readLine()) != null) {
			ByteArrayInputStream bais=new ByteArrayInputStream(line.getBytes());
			try{
				Properties prop=new Properties();
				prop.load(bais);
				Set<Entry<Object, Object>> entrySet=prop.entrySet();
				for(Entry<Object, Object> e:entrySet){
					if(language.getIso().equals("ru"))
						System.out.println(e.getKey() + ": " + e.getValue());
					res.put(e.getKey().toString(), e.getValue().toString());
				}
			}
			catch (Exception e) {
				err.add(language.getIso() + " " + i + " : " + line);
			}
			finally{
				i++;
			}
		}
		Pair<Map<String, String>, List<String>> ret = new Pair<Map<String, String>, List<String>>(res, err);
		return ret;
	}
}
