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
}
