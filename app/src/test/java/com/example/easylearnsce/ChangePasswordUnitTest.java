package com.example.easylearnsce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChangePasswordUnitTest {
    @Test
    public void isPasswordValid() {
        assertTrue(isPasswordValid("123456"));
        assertTrue(isPasswordValid("asd3211c!"));
        assertFalse(isPasswordValid("t"));
        assertFalse(isPasswordValid("aaa55"));
        assertFalse(isPasswordValid("12345"));
        assertEquals("123456","123456");
        assertEquals("1a2b3c4d5e6f","1a2b3c4d5e6f");
        assertNotEquals("123456","1a2b3c4d5e6f");
    }
    public boolean isPasswordValid(CharSequence password) {
        return password.length() >= 6;
    }
}
