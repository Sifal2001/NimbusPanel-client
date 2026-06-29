package com.Client.WeatherCast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import com.google.gson.Gson;

public class WeatherCastController {
	public static WeatherCast getWeatherCast(String[] args, float Lat, float Lon)
	        throws weatherCallException, IOException, URISyntaxException {
	    String apiUrl = "http://localhost:8080/api/v1/castRequest?lat=" + Lat + "&lon=" + Lon;
	    URI uri = new URI(apiUrl);
	    URL url = uri.toURL();
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");

	    int responseCode = conn.getResponseCode();
	    if (responseCode != 200) {
	        throw new weatherCallException("Error: " + responseCode);
	    }

	    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    StringBuilder response = new StringBuilder();
	    String inputLine;
	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();

	    Gson gson = new Gson();
	    
	    System.out.println("offset in json: look for timezone_offset");
	    System.out.println(response.toString());
	    return gson.fromJson(response.toString(), WeatherCast.class);

	}
	
	public static class weatherCallException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public weatherCallException(String message) {
			super(message);
		}
		
	}
}
