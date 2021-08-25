package com.kris.admin.route;

import com.kris.common.core.util.SysUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** @Author: kris @Date: 2021/7/6 @Description: @Since: JDK11 */
@SpringBootApplication
@EnableTransactionManagement
public class RouteApplication {

  public static void main(String[] args) throws UnknownHostException {
    SysUtil.run(RouteApplication.class, args);
  }
}
