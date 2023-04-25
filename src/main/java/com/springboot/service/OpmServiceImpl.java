package com.springboot.service;

import com.yuntai.hdp.access.service.AccessHospitalHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Package: com.springboot.service
 * @Author: denglt
 * @Date: 2023/4/24 14:06
 * @Copyright:
 */
@Service
public class OpmServiceImpl {

    @Resource
    private AccessHospitalHandler accessHospitalHandler;
}
