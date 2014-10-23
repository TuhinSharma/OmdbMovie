package generatedataset;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GenerateIMDBid {

	public static String delimiter = " ";

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		RandomAccessFile file = new RandomAccessFile("data/ratings.list", "r");

		FileWriter newfile = new FileWriter("newList.txt");
		int xx=0;

		String line;
		for (int i = 0; i < 296; i++)
			line = file.readLine();
		int j = 296;
		String movie = " ";
		String movie_prev = " a";
		String date = " ";
		String date_prev = " asa";
		while ((line = file.readLine()) != null) {
			j++;
			int flag = 0;
			int count = 0;
			int lastindex = 0;
			int firstindex = 0;
			int foundbracket = 0;
			int firstdate = 0;
			int firstfound = 0;
			for (int index = 0; index < line.length(); index++) {

				if (line.charAt(index) == ' ') {
					// System.out.println("space");
					flag = 0;
				} else if (flag == 0) {
					count++;
					flag = 1;
					// Thread.sleep(2000);
					// System.out.println("char");
				}

				if (line.charAt(index) == '(' && foundbracket == 0
						&& count >= 5) {
					// System.out.println(count);
					// Thread.sleep(1000);
					lastindex = index - 1;
					firstdate = index + 1;
					// System.out.println(lastindex);
					foundbracket = 1;
				}

				if (count == 4 && firstfound == 0) {
					firstindex = index;
					firstfound = 1;

				}
			}
			if (line.charAt(firstindex) == '"')
				firstindex++;
			if (line.charAt(lastindex - 1) == '"') {
				lastindex--;
			}
			if (line.charAt(lastindex - 1) == '!') {
				lastindex--;
			}

			movie = line.substring(firstindex, lastindex);
			date = line.substring(firstdate, firstdate + 4);
			if (!movie.equals(movie_prev) && !date.equals(date_prev)) {
				xx++;
				newfile.write(movie + "\n");
				newfile.write(date + "\n");
			}
			movie_prev = movie;
			date_prev = date;
			System.out.println(j+" "+xx);
		}
		file.close();
		newfile.close();

	}
}