package com.example.easylearnsce;
import org.junit.Test;
import static org.junit.Assert.*;
import androidx.core.util.PatternsCompat;

public class ResetPasswordUnitTest {
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
}