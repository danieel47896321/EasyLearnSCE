package com.example.easylearnsce;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ChatUnitTest {
    @Test
    public void isMessageValid() {
        assertTrue(isMessageValid("dani"));
        assertTrue(isMessageValid("dor"));
        assertTrue(isMessageValid("מה המצב?"));
        assertFalse(isMessageValid(""));
    }
    public boolean isMessageValid(String name) {
        return name.length() > 0;
    }
}
