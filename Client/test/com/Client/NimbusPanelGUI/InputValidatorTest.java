package com.Client.NimbusPanelGUI;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void standardEmail_isValid() {
        assertTrue(InputValidator.isValidEmail("john@example.com"));
    }

    @Test
    void emailWithoutAtSign_isInvalid() {
        assertFalse(InputValidator.isValidEmail("johnexample.com"));
    }

    @Test
    void emailWithoutDomain_isInvalid() {
        assertFalse(InputValidator.isValidEmail("john@"));
    }

    @Test
    void nullEmail_isInvalid() {
        assertFalse(InputValidator.isValidEmail(null));
    }

    @Test
    void normalFirstName_returnsNull() {
        assertNull(InputValidator.validateFirstName("John"));
    }

    @Test
    void emptyFirstName_returnsRequiredError() {
        assertEquals("First Name is required", InputValidator.validateFirstName(""));
    }

    @Test
    void firstNameWithNumbers_returnsLettersError() {
        assertEquals("First name must only contain letters",
                     InputValidator.validateFirstName("John123"));
    }
    
    @Test
    void matchingValidPasswords_returnNull() {
        assertNull(InputValidator.validatePasswords("password123", "password123"));
    }

    @Test
    void shortPassword_returnsLengthError() {
        assertEquals("Please enter a password with at least 8 characters",
                     InputValidator.validatePasswords("short", "short"));
    }

    @Test
    void mismatchedPasswords_returnMismatchError() {
        assertEquals("Password does not match",
                     InputValidator.validatePasswords("password123", "password999"));
    }

    @Test
    void emptyPassword_returnsEnterError() {
        assertEquals("Please enter a password",
                     InputValidator.validatePasswords("", ""));
    }
}
