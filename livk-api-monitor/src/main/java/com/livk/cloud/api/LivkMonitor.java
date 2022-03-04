package com.livk.cloud.api;

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
public class LivkMonitor {

	public static void main(String[] args) {
		LivkSpring.run(LivkMonitor.class, args);
	}

}
