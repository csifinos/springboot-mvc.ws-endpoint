package com.github.csifinos.wsendpoint;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import com.github.csifinos.wsendpoint.websocket.pubsub.PubSubProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({WsProperties.class, PubSubProperties.class})
public class WsEndpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsEndpointApplication.class, args);
	}

}
