package edu.ranken.emeier.loginscreen;

public class PasswordValidator {

    /*
        min length is 6
        at least one non-alphanumeric character
        can't contain 1234
        can't include whitespace (spaces, tabs, new lines)
     */

    public boolean isValid(String password) {
        boolean containsAlphanumeric = false;

        // if the password is null
        // if the password is shorter than min-length
        // if password contains sequential numbers
        if (password == null || password.length() < 6 || password.contains("1234")) {
            return false;
        }

        // loop through the characters in the password
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);

            // check if the current character is any form of whitespace
            if (currentChar == '\r' || currentChar == '\n' || currentChar == '\t' || currentChar == ' ') {
                return false;
            }

            if (!Character.isLetterOrDigit(currentChar)) {
                containsAlphanumeric = true;
            }
        }

        return containsAlphanumeric;
    }

}