package generatedataset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CrawlIMDBdata {

	public static String static_url = "http://www.omdbapi.com/?t=";
	public static String url1 = "&y=";

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

	public static String GetImdbId(String movie) throws IOException, JSONException {

		String url1 = movie.replace("%", "%25").replace(" ", "%20")
				.replace("!", "%21").replace("\"", "%22").replace("#", "%23")
				.replace("$", "%24").replace("&", "%26").replace("\'", "%27")
				.replace("(", "%28").replace(")", "%29").replace("*", "%2a")
				.replace("+", "%2b").replace(",", "%2c").replace("-", "%2d")
				.replace(".", "%2e").replace("/", "%2f");
		// System.out.println(movie);
		// System.out.println(url1);

		String url = static_url + url1;

		String jsonStr = getJsonString(url);
		JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a
															// JSONObject
		if (rootObject.get("Response").equals("True"))
			return (String) rootObject.get("imdbID");
		else
			return null;

	}

	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub

		RandomAccessFile file = new RandomAccessFile("newList.txt", "r");

		String line = " ";

		int count = 0;



		while ((line = file.readLine()) != null) {
			FileWriter notfoundfile = new FileWriter("NotFoundMovies.txt",true);
			FileWriter foundfile = new FileWriter("FoundMovies.txt",true);
			count++;
			String movie_name = line;
			String date = file.readLine();

			String imdb_id = GetImdbId(movie_name);
			if(imdb_id==null)
				notfoundfile.write(movie_name+"\n");
			else
				foundfile.write(imdb_id+"\n");
			System.out.println(count);

			notfoundfile.close();
			foundfile.close();
		}

		file.close();

	}

}
