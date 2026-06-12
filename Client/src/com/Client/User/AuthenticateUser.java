package com.Client.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class AuthenticateUser{
	
	public static class AuthenticateUserResponse{
		
		public int code;
		public String body;
		
		public AuthenticateUserResponse(int code, String body) {
			this.code = code;
			this.body = body;
		}
	}
	
	public static AuthenticateUserResponse authenticateUser(String email, String password) throws Exception {
	    String apiUrl = "http://localhost:8080/api/v1/user/authenticate";
	    URI uri = new URI(apiUrl);
	    URL url = uri.toURL();
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setDoOutput(true);
	
	    Gson gson = new Gson();
        AuthenticateRequest loginRequest = new AuthenticateRequest(email, password);
        String json = gson.toJson(loginRequest);
	    
	    try (OutputStream os = conn.getOutputStream()) {
	        byte[] input = json.getBytes(StandardCharsets.UTF_8);
	        os.write(input, 0, input.length);
	    }
	
	    int responseCode = conn.getResponseCode();
	    InputStream responseStream =  responseCode < 400 ? conn.getInputStream() : conn.getErrorStream();
	
	    StringBuilder response = new StringBuilder();
	    if (responseStream != null) {
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }
	        }
	    }
        
        if (responseCode >= 400) {
            throw new ApiException(responseCode, response.toString());
        }
        
        return new AuthenticateUserResponse(responseCode, response.toString());
	    }

	public static class ApiException extends Exception{

		private static final long serialVersionUID = -461448699498815105L;
		
		public final int statusCode;
	
		public ApiException(int statusCode, String message) {
			super(message);
			this.statusCode = statusCode;
		}
	}
}

	