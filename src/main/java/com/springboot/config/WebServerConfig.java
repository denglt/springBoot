package com.springboot.config;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Web服务器的配置
 * @see ServerProperties  ServletWebServerFactoryAutoConfiguration
 */
@Configuration
public class WebServerConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     *  Customize the specified WebServerFactory
     * @param server
     */

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        System.out.println("WebServerFactory -> " + server);
       // server.setPort(9000);
        if (server instanceof TomcatServletWebServerFactory){
            TomcatServletWebServerFactory tomcatServletWebServerFactory =(TomcatServletWebServerFactory) server;
            tomcatServletWebServerFactory.addConnectorCustomizers( connector -> {
              // connector.setAsyncTimeout();
              //  connector.setPort();
                ProtocolHandler protocolHandler = connector.getProtocolHandler();
                if (protocolHandler instanceof AbstractProtocol){
                    AbstractProtocol protocol = (AbstractProtocol) protocolHandler;
                    protocol.setKeepAliveTimeout(10000);
                   // protocol.setSoTimeout();

                }
            });
        }
    }
}
