package com.example.easylearnsce;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.core.util.PatternsCompat;

import org.junit.Test;

public class CreateAccountUnitTest {
    @Test
    public void isNameValid() {
        assertTrue(isNameValid("dani"));
        assertTrue(isNameValid("dor"));
        assertTrue(isNameValid("omer"));
        assertFalse(isNameValid("test!"));
        assertFalse(isNameValid("t@com"));
        assertFalse(isNameValid("t@test11"));
    }
    public boolean isNameValid(String name) {
        return name.length() > 0 && !name.contains("@") && !name.contains(".") && !name.contains("!") && !name.contains("#");
    }
    @Test
    public void isEmailValid() {
        assertTrue(isEmailValid("test@gmail.com"));
        assertTrue(isEmailValid("test112312@gmail.com"));
        assertTrue(isEmailValid("test_11@gmail.com"));
        assertFalse(isEmailValid("t"));
        assertFalse(isEmailValid("t@@"));
        assertFalse(isEmailValid("t@123"));
        assertFalse(isEmailValid("t@com"));
    }
    public boolean isEmailValid(CharSequence email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Test
    public void isPasswordValid() {
        assertTrue(isPasswordValid("123456"));
        assertTrue(isPasswordValid("asd3211c!"));
        assertFalse(isPasswordValid("t"));
        assertFalse(isPasswordValid("aaa55"));
        assertFalse(isPasswordValid("12345"));
    }
    public boolean isPasswordValid(CharSequence password) {
        return password.length() >= 6;
    }
}
