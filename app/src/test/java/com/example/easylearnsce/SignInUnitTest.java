package com.example.easylearnsce;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.core.util.PatternsCompat;

import org.junit.Test;

public class SignInUnitTest {
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
