package com.ehs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;


@SpringBootApplication
@EnableMongoHttpSession(maxInactiveIntervalInSeconds = com.ehs.common.auth.utils.SessionConstants.MAX_TIME_OUT)
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class GspApplication {

	public static void main(String[] args) {
		SpringApplication.run(GspApplication.class, args);
	}

}
