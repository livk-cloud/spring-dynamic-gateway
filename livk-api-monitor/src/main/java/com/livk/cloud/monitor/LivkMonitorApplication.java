package com.livk.cloud.monitor;

import com.livk.common.core.spring.LivkSpring;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * LivkMonitor
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@EnableAdminServer
@SpringBootApplication
public class LivkMonitorApplication {

	public static void main(String[] args) {
		LivkSpring.run(LivkMonitorApplication.class, args);
	}

}
