package com.app;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@WebAppConfiguration
public class AppTests {

    @Test
    public void contextLoads() {
        assertTrue("Context Loads", true);
    }

}
