package com.springboot.config;

import com.yuntai.hdp.access.service.AccessHospitalHandler;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2023/4/18 11:44
 * @Copyright:
 */
@Configuration
public class HdpAccessConfig {

    /*
         ReferenceAnnotationBeanPostProcessor 将会在spring中注册一个ReferenceBean<AccessHospitalHandler>
         其他Service 可以使用 @Autowired 直接获取
     */
    @DubboReference(timeout = 60000, retries = 0 ,check = false)
    private AccessHospitalHandler accessHospitalHandler;
}
