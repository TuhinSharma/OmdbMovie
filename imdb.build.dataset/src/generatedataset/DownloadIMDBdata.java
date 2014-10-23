package generatedataset;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

public class DownloadIMDBdata {
	public static String static_url1 = "http://www.omdbapi.com/?i=tt";
	public static String static_url2 = "&r=JSON&plot=full&tomatoes=true";

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static String getJsonString(String url) throws IOException,
			JSONException {
		JSONObject json = readJsonFromUrl(url);
		return (json.toString());
	}

	public static JSONObject GetImdbId(String movie_id) throws IOException,
			JSONException {

		String url = static_url1 + movie_id + static_url2;

		String jsonStr = getJsonString(url);
		JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a
															// JSONObject
		if (rootObject.get("Response").equals("True"))
			return rootObject;
		else
			return null;

	}

	public static FileWriter createFile() throws InterruptedException,
			IOException {
		// TODO Auto-generated method stub
		String filepath = createFilePathforJSON();
		FileWriter newfile = new FileWriter(filepath);
		return newfile;
	}

	public static String createFilePathforJSON() throws InterruptedException {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		String k = dateFormat.format(cal.getTime());
		String filepath = "imdbdata/" + k + ".txt";
		// System.out.println(filepath);
		return filepath;
	}

	public static void main(String[] args) throws InterruptedException,
			IOException, JSONException {
		// TODO Auto-generated method stub

		FileWriter file = createFile();

		for (int i = 1; i < 4003997; i++) {
			String s = String.format("%07d", i);
			JSONObject imdbdata = GetImdbId(s);
			file.write(imdbdata+"\n");
			if(i%5000==0){
				file.close();
				file = createFile();
			}
			
		}

	}

}
// 4003996