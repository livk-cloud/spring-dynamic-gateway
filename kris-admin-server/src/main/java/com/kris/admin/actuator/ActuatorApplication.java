package com.kris.admin.actuator;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/7/7 21:45
 * @since JDK 11
 */
@Slf4j
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class ActuatorApplication {

  public static void main(String[] args) throws UnknownHostException {
    Environment env = SpringApplication.run(ActuatorApplication.class, args).getEnvironment();
    log.info("Gateway地址：\thttp://{}:{}", InetAddress.getLocalHost().getHostAddress(),
        env.getProperty("server.port"));
  }
}
