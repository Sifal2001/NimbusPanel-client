package com.Client.CoordResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import com.Client.WeatherCast.WeatherCast;
import com.Client.WeatherCast.WeatherCastController;
import com.Client.WeatherCast.WeatherCastController.weatherCallException;
import com.google.gson.Gson;


public class Location {
	
	public static WeatherCast GetLocation(String args[], String city) throws IOException,
    		ApiException, URISyntaxException, weatherCallException {
		String apiUrl = "http://localhost:8080/api/v1/castLocation?q=" + city;
		URI uri = new URI(apiUrl);
		URL url = uri.toURL();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		int responseCode = conn.getResponseCode();
		
		if (responseCode != 200) {
		    throw new ApiException("Error :" + responseCode);
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder response = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		}
		in.close();
		
		Gson gson = new Gson();
		CoordResponse responseArray = gson.fromJson(response.toString(), CoordResponse.class);
		Coord coord = responseArray.getCoord();
		
		float Lon = coord.getLon();
		float Lat = coord.getLat();
		
		return WeatherCastController.getWeatherCast(new String[]{}, Lat, Lon);
	}
	
	
	public static class ApiException extends Exception{

		private static final long serialVersionUID = -461448699498815105L;

		public ApiException(String message) {
			super(message);
		}
		
	}

}
	
