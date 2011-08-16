package au.edu.usyd.it.tagClustering;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Translate 
{
	
//	https://www.googleapis.com/language/translate/v2?key=AIzaSyCS9WvUD--kFrdkvufL5jKMmM9VdvhRPyo&q=google+%E4%BD%A0%E5%A5%BD+is+fast&target=en
	
	public String[] translate(String query)
	{
		String[] arr = null;
		try
		{
			String str = "https://www.googleapis.com/language/translate/v2?key=AIzaSyCS9WvUD--kFrdkvufL5jKMmM9VdvhRPyo&q=" + query + "&target=en";
			URL callUrl = new URL(str);
			URLConnection urlConn = callUrl.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String line ;
			String result = "";
			while((line = reader.readLine()) != null)
			{
				if(line.contains("translatedText"))
				{
					result = (line.substring(line.indexOf(":") + 3, line.lastIndexOf("\"")));
				}
			}
			arr = result.trim().split(" ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return arr;
	}
}
