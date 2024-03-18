package com.twendeno.msauth;

import com.twendeno.msauth.securityConfig.DotEnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableScheduling
@EnableMethodSecurity
@SpringBootApplication
public class MsAuthApplication {

	public static void main(String[] args) {
		DotEnvConfig.loadEnv();
		SpringApplication.run(MsAuthApplication.class, args);

	}

}
