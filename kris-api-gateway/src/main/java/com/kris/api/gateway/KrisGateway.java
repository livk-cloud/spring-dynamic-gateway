package com.kris.api.gateway;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @Author: kris
 * @Date: 2021/7/12
 * @Description:
 * @Since: JDK11
 */
@Slf4j
@SpringBootApplication
public class KrisGateway {

  public static void main(String[] args) throws UnknownHostException {
    Environment env = SpringApplication.run(KrisGateway.class, args).getEnvironment();
    log.info("地址：\thttp://{}:{}", InetAddress.getLocalHost().getHostAddress(),
        env.getProperty("server.port"));
  }
}
