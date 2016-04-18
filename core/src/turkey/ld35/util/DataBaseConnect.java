package turkey.ld35.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

public class DataBaseConnect
{
	public static void sendData(String userName, int score, int wave)
	{
		try
		{
			HttpURLConnection con = (HttpURLConnection) new URL("http://theprogrammingturkey.com/API/LD35LeaderBoard.php?userName=" + userName + "&score=" + score + "&wave=" + wave).openConnection();
			con.setReadTimeout(20000);
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);

			int responseCode = con.getResponseCode();

			if(responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_MOVED_PERM)
				Gdx.app.log("Warn", "Update request returned response code: " + responseCode + " " + con.getResponseMessage());
			else if(responseCode == HttpURLConnection.HTTP_MOVED_PERM)
				Gdx.app.log("Warn", "URL was moved! Response code: " + responseCode + " " + con.getResponseMessage());
		} catch(IOException e)
		{
		}
	}

	public static List<CustomEntry<String, String>> getLeaderBoard()
	{
		try
		{
			HttpURLConnection con = (HttpURLConnection) new URL("http://theprogrammingturkey.com/API/LD35LeaderBoardGet.php").openConnection();
			con.setReadTimeout(20000);
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);

			BufferedInputStream in = new BufferedInputStream(con.getInputStream());
			int responseCode = con.getResponseCode();

			if(responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_MOVED_PERM)
				Gdx.app.log("Warn", "Update request returned response code: " + responseCode + " " + con.getResponseMessage());
			else if(responseCode == HttpURLConnection.HTTP_MOVED_PERM)
				Gdx.app.log("Warn", "URL was moved! Response code: " + responseCode + " " + con.getResponseMessage());

			List<CustomEntry<String, String>> leaderBoard = new ArrayList<CustomEntry<String, String>>();
			StringBuffer buffer = new StringBuffer();
			int chars_read;
			while((chars_read = in.read()) != -1)
			{
				buffer.append((char) chars_read);
			}

			String[] entries = buffer.toString().split("<br>");
			for(String s : entries)
			{
				s = s.trim();
				int loc = s.indexOf(",");
				if(loc == -1)
					continue;
				leaderBoard.add(new CustomEntry<String, String>(s.substring(0, loc), s.substring(loc + 1)));
			}

			return leaderBoard;

		} catch(IOException e)
		{
		}
		return new ArrayList<CustomEntry<String, String>>();
	}
}
