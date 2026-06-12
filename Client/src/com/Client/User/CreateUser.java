package com.Client.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import com.google.gson.Gson;

public class CreateUser{
	
	public static class CreateUserResponse{
		
		public int code;
		public String body;
		
		public CreateUserResponse(int code, String Body) {
			this.code = code;
		}
	}
	
	public static CreateUserResponse createUser(String userJson) throws Exception {
	    String apiUrl = "http://localhost:8080/api/v1/user";
	    URI uri = new URI(apiUrl);
	    URL url = uri.toURL();
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setDoOutput(true);
	
	    try (OutputStream os = conn.getOutputStream()) {
	        byte[] input = userJson.getBytes("utf-8");
	        os.write(input, 0, input.length);
	    }
	
	    int responseCode = conn.getResponseCode();
	    if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {
	        throw new ApiException("Error: " + responseCode);
	    }
	
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }
	
	    return new CreateUserResponse(responseCode, response.toString());
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
	