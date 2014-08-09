package diploma.webcad.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static String getResponseByGet(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), Charset.forName("utf-8")));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getResponseByPost(String request) {
		String result = null;
		try {
			HttpPost post = new HttpPost(request);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			result = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Properties getUrlProperties (String url) {
		Properties properties = new Properties();
		List<NameValuePair> valuePairs = null;
		try {
			valuePairs = URLEncodedUtils.parse(new URI(url), "UTF-8");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (valuePairs != null) {
			for (NameValuePair valuePair : valuePairs) {
				String value = valuePair.getValue();
				if (value == null) { value = ""; }
				properties.put(valuePair.getName(), value);
			}
		}
		return properties;
	}

}
