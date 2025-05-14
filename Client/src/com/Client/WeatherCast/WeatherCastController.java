package com.Client.WeatherCast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.Client.NimbusPanelGUI.Main;
import com.google.gson.Gson;

public class WeatherCastController {
	public static String getWeatherCast(String[] args, float Lat, float Lon) throws weatherCallException, IOException, URISyntaxException {
    	String apiUrl = "http://localhost:8080/api/v1/castRequest?lat=" + Lat + "&lon=" + Lon;
            URI uri = new URI(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            
            if(responseCode != 200) {
            	throw new weatherCallException("Error: " + responseCode) ;
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            WeatherCast responseArray = gson.fromJson(response.toString(), WeatherCast.class);
            
            
            /*
             * Get Current weather data from json 
             */
            CurrentCast currentWeather = responseArray.getCurrent();
            Main.current_temp = currentWeather.getTemp();
            Main.current_humidity = currentWeather.getHumidity();
            Main.current_feelslike = currentWeather.getFeels_like();
            Main.current_windspeed = currentWeather.getWind_speed();
            Main.current_sunrise_timestamp = currentWeather.getSunrise();
            Main.current_sunset_timestamp = currentWeather.getSunset();
            Main.current_time = currentWeather.getDt();
                          
            for (WeatherCondition weather : currentWeather.getWeather()) {
            	Main.current_description = weather.getMain();         	
            	System.out.println("des: " + Main.current_description);
            }
            
            
            /*
             * Get hourly weather data from json
             */               
            List <HourlyCast> hourlyWeather = responseArray.getHourly();
            
            for(int i = 0; i < 24; i++) {
            	HourlyCast hourly = hourlyWeather.get(i);
            	Main.hourly_temp[i] = hourly.getTemp();	
            	Main.hours[i] = hourly.getDt();
            	System.out.print(Main.hourly_temp[i]);

                for (WeatherCondition weather : hourly.getWeather()) {
                	Main.hourly_description[i] =  weather.getMain();         	
                	System.out.println("daily des: " + Main.hourly_description[i]);
                }
            }
            
            
            /*
             * Get daily weather data from json
             */       
            List <DailyCast> dailyWeather = responseArray.getDaily();
            
            for (int i = 0; i < 6; i++) {
				DailyCast daily = dailyWeather.get(i);
				Main.days[i] = daily.getDt();
				Main.daily_temp[i] = daily.getTemp().getDay();
				Main.daily_humidity[i] = daily.getHumidity();	
				
                for (WeatherCondition weather : daily.getWeather()) {
                	Main.daily_description[i] =  weather.getMain();         	
                	System.out.println("daily des: " + Main.daily_description[i]);
                }
			}         
            System.out.println("this one" + response.toString());
            
            
            return response.toString();
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
