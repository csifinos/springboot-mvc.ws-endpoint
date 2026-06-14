package com.github.csifinos.wsendpoint;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WsProperties.class)
public class WsEndpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsEndpointApplication.class, args);
	}

}
