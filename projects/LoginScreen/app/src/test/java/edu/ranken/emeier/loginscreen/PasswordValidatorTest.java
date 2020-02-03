package edu.ranken.emeier.loginscreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class PasswordValidatorTest {

    private PasswordValidator mValidator;

    @Before
    public void setup() {
        mValidator = new PasswordValidator();
    }

    /*
        min length is 6
        at least one non-alphanumeric character
        can't contain 1234
        can't include whitespace (spaces, tabs, new lines)
     */

    @Test
    public void validPassword() {
        assertThat(mValidator.isValid("P@ssw0rd"), is(equalTo(true)));
    }

    @Test
    public void exactlyMinLength() {
        assertThat(mValidator.isValid("P@ssw0"), is(equalTo(true)));
    }

    @Test
    public void containsSequentialDigits() {
        assertThat(mValidator.isValid("abcd1234!"), is(equalTo(false)));
    }

    @Test
    public void tooShort() {
        assertThat(mValidator.isValid("P@ss"), is(equalTo(false)));
    }

    @Test
    public void alphanumericOnly() {
        assertThat(mValidator.isValid("abc123"), is(equalTo(false)));
    }

    @Test
    public void password123456() {
        assertThat(mValidator.isValid("123456"), is(equalTo(false)));
    }

    @Test
    public void emptyString() {
        assertThat(mValidator.isValid(""), is(equalTo(false)));
    }

    @Test
    public void nullString() {
        assertThat(mValidator.isValid(null), is(equalTo(false)));
    }

    @Test
    public void allSpaces() {
        assertThat(mValidator.isValid("        "), is(equalTo(false)));
    }

    @Test
    public void containsWhitespaceAtEnds() {
        assertThat(mValidator.isValid(" P@ssw0rd "), is(equalTo(false)));
    }

    @Test
    public void containsSpaceInMiddle() {
        assertThat(mValidator.isValid("P@ss w0rd"), is(equalTo(false)));
    }

    @Test
    public void containsWhitespaceInMiddle() {
        assertThat(mValidator.isValid("P@ss\t\r\nw0rd"), is(equalTo(false)));
    }
}