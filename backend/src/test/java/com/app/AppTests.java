package com.app;

import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;

@WebAppConfiguration
public class AppTests extends BaseTest {

    @Test
    public void contextLoads() {
        assertTrue("Context Loads", true);
    }

}
