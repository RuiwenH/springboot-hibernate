package com.reven.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired
    private MockMvc mvc;

    @Test
    public void testList() throws Exception {
        MvcResult result= mvc.perform(MockMvcRequestBuilders.get("/user/list")).andReturn();
//        action.andExpect(MockMvcResultMatchers.status().isOk());
//        action.andExpect(MockMvcResultMatchers.content().string("reven")); // 测试接口返回内容
        logger.info(result.getResponse().getContentAsString());
        
    }

}
