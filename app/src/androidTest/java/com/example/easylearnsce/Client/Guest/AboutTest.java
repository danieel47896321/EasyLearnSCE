package com.example.easylearnsce.Client.Guest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AboutTest {
    @Rule
    private ActivityScenarioRule<About> AboutActivity;
    @Before
    public void setUp() throws Exception {
        AboutActivity = new ActivityScenarioRule<>(About.class);
    }
    @Test
    public void testLaunch(){


    }
    @After
    public void tearDown() throws Exception {
        AboutActivity.getScenario().close();
    }
}