package com.springboot.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DemoApplicationTests {

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() {
        // 获取配置的数据源
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        // 查看配置数据源信息
        System.out.println(dataSource);
        System.out.println(dataSource.getClass().getName());
        System.out.println("DataSourceProperties -> " + dataSourceProperties);

    }


    @Test
    public void syncRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andExpect(MockMvcResultMatchers.content().string("Honda Civic"))
                .andReturn();
        System.out.println(mvcResult);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void asyncRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/async/shortCallable"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andExpect(MockMvcResultMatchers.content().string("Honda Civic"))
                .andReturn();
        System.out.println(mvcResult);
        System.out.println("同步结果：" + mvcResult.getResponse().getContentAsString());
        System.out.println("异步结果：" + mvcResult.getAsyncResult());
    }

}
