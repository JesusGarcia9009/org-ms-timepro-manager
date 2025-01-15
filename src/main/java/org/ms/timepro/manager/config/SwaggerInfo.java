package org.ms.timepro.manager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "swagger.api.info")
public class SwaggerInfo {
	
	private String title;

    private String version;
    
    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String license;

}
