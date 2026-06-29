package com.Client.NimbusPanelGUI;

public final class InputValidator {

    private InputValidator() {}   

    public static boolean isValidEmail(String email) {
        return email != null
            && email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
    }

    public static String validateFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return "First Name is required";
        }
        if (!firstName.matches("[a-zA-Z]*")) {
            return "First name must only contain letters";
        }
        return null;  
    }
    
    public static String validateLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            return "Last Name is required";
        }
        if (!lastName.matches("[a-zA-Z]*")) {
            return "Last name must only contain letters";
        }
        return null;   
    }

    public static String validatePasswords(String password, String passwordConfirm) {
        if (password.isEmpty()) {
            return "Please enter a password";
        }
        if (passwordConfirm.isEmpty()) {
            return "Please confirm your password";
        }
        if (password.length() < 8) {
            return "Please enter a password with at least 8 characters";
        }
        return password.equals(passwordConfirm) ? null : "Password does not match";
    }
}