package com.Client.User;

import java.io.IOException;
//import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;
import com.Client.User.GetEmails.ApiException;
import com.google.gson.Gson;

public class UserService {

    public boolean isEmailDuplicate(String email) {
        try {
            List<String> emails = GetEmails.getAllEmails();
            for (String emailFromDB : emails) {
                if (emailFromDB.equals(email)) {
                    return true;
                }
            }
        } catch (IOException | ApiException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public CreateUser.CreateUserResponse createUser(String firstName, String lastName, String email, String password) throws Exception {
        User user = new User(firstName, lastName, email, password, null);
        String userJson = new Gson().toJson(user);
        return CreateUser.createUser(userJson);
    }
}
