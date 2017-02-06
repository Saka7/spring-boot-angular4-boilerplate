package com.app.controller;

import com.app.BaseTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebAppConfiguration
public abstract class AbstractControllerTest extends BaseTest {

    protected MockMvc mvc;

    protected void setUp(BaseController controller) {
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

}