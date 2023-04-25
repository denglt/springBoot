package com.springboot.service;

import com.yuntai.hdp.access.service.AccessHospitalHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Package: com.springboot.service
 * @Author: denglt
 * @Date: 2023/4/24 11:20
 * @Copyright:
 */
@Service("aTestService")
public class TestServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private AccessHospitalHandler accessHospitalHandler;

    public TestServiceImpl(){
        logger.info("AccessHospitalHandler -> {}",accessHospitalHandler);
    }
}
