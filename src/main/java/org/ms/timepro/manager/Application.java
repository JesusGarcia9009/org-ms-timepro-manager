package org.ms.timepro.manager;


import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ImportAutoConfiguration(org.jwt.security.autoconfiguration.SecuritySessionAutoConfigure.class)
@EntityScan(basePackages = "org.ms.timepro.manager.entity")
@EnableJpaRepositories(basePackages = {"org.ms.timepro.manager.repository"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
