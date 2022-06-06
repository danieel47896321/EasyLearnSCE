package com.example.easylearnsce;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProfileUnitTest {
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
}
