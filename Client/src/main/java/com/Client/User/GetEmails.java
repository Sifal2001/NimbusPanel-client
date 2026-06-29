package com.Client.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;


public class GetEmails {
	
	public static List<String> getAllEmails() throws IOException, 
	ApiException, URISyntaxException {
    	String apiUrl = "http://localhost:8080/api/v1/user/emails";
            URI uri = new URI(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            
            if(responseCode != 200) {
            	throw new ApiException("Error :" + responseCode);   	
            }
            
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }

            reader.close();
            conn.disconnect();

            Gson gson = new Gson();

            
            return gson.fromJson(response.toString(), new TypeToken<List<String>>(){}.getType());    
    }
	
	
	public static class ApiException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = -461448699498815105L;

		public ApiException(String message) {
			super(message);
		}
		
	}

}
	