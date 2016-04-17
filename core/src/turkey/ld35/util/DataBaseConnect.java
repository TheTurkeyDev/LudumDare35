package turkey.ld35.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Gdx;

public class DataBaseConnect
{
	public static void sendData(String userName, int score, int wave)
	{
		try
		{
			HttpURLConnection con = (HttpURLConnection) new URL("http://theprogrammingturkey.com/API/LD35LeaderBoard.php?userName=" + userName + "&score=" + score + "&wave=" + wave).openConnection();
			con.setDoOutput(false);
			con.setReadTimeout(20000);
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
			((HttpURLConnection) con).setRequestMethod("GET");

			int responseCode = con.getResponseCode();

			if(responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_MOVED_PERM)
				Gdx.app.log("Warn", "Update request returned response code: " + responseCode + " " + con.getResponseMessage());
			else if(responseCode == HttpURLConnection.HTTP_MOVED_PERM)
				Gdx.app.log("Warn", "URL was moved! Response code: " + responseCode + " " + con.getResponseMessage());
		} catch(IOException e)
		{
		}
	}
}
