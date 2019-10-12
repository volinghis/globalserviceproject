package com.ehs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = com.ehs.common.auth.utils.SessionConstants.MAX_TIME_OUT)
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class GspApplication {

	public static void main(String[] args) {
		SpringApplication.run(GspApplication.class, args);
	}

}