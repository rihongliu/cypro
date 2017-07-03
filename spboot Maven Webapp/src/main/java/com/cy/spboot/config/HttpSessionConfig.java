package com.cy.spboot.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 24 * 60 * 60)
@EnableRedisHttpSession 
public class HttpSessionConfig {
}
