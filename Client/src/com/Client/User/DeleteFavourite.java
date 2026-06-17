package com.Client.User;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class DeleteFavourite {
    public static void deleteFavourite(Long id, Long favouriteId) throws Exception {
        String apiUrl = "http://localhost:8080/api/v1/user/" + id + "/favourites/" + favouriteId;
        URI uri = new URI(apiUrl);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_NO_CONTENT
                && responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Error deleting favourite: " + responseCode);
        }
    }
}