package org.ms.timepro.manager;


import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@ImportAutoConfiguration(org.jwt.security.autoconfiguration.SecuritySessionAutoConfigure.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
