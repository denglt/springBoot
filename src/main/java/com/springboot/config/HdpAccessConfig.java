package com.springboot.config;

import com.yuntai.hdp.access.RequestPack;
import com.yuntai.hdp.access.ResultPack;
import com.yuntai.hdp.access.service.AccessHospitalHandler;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
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
         dubbo 2.7.15
         ReferenceAnnotationBeanPostProcessor 将会在spring中注册一个ReferenceBean<AccessHospitalHandler>
         其他Service 可以使用 @Resource @Autowired 直接获取
         但是这儿有个必要的前提条件：
             配置@DubboReference的类必须在需要注入对象前进行创建。

         在spring中如何保证某个对象先创建，没有什么好方法（指定@Order是不行的），
         因为spring是根据相互依赖关系来创建对象的，但是spring在容器创建过程中无法感知到@DubboReference，也就无法确定依赖关系。

         如何保证配置@DubboReference的类先创建呢？
         解决方法：
            1、配置类搜索路径，路径在前的对象会被先search出来并排在前面会被优先创建;
            eg:
              @SpringBootApplication(scanBasePackages = {"com.springboot.config","dlt.utils.spring","com.springboot","com.yuntai"} )
            com.springboot.config 路径下的类会被优先创建

            2、在结合使用@DependsOn，进行微调
            @DependsOn(value = {"hdpAccessConfig"})
        好麻烦：主要是dubbo spring 的 @DubboReference 实现机制不是很好.
        如果支持：就爽了。网上有人这样写，但测试dubbo 2.7.15不行，不知 dubbo 3是否支持？
           @Bean
           @DubboReference(timeout = 60000, retries = 0 ,check = false)
           public ReferenceBean<AccessHospitalHandler> accessHospitalHandler(){
               return new ReferenceBean<>();
           }
     */

   // @DubboReference(timeout = 60000, retries = 0 ,check = false)
   private AccessHospitalHandler accessHospitalHandler;
   // 去掉 dubbo，这儿 new 一个 AccessHospitalHandler
   @Bean
   public AccessHospitalHandler accessHospitalHandler(){
       return (request, timeout) -> null;
   }

}
