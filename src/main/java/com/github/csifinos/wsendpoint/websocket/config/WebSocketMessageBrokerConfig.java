package com.github.csifinos.wsendpoint.websocket.config;

import com.github.csifinos.wsendpoint.websocket.handshake.WsHandshakeHandler;
import com.github.csifinos.wsendpoint.websocket.handshake.WsHandshakeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Component
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private final WsHandshakeInterceptor wsHandshakeInterceptor;
    private final WsHandshakeHandler wsHandshakeHandler;
    private final WsProperties wsProperties;
    private final ThreadPoolTaskScheduler brokerTaskScheduler;

    public WebSocketMessageBrokerConfig(WsHandshakeInterceptor wsHandshakeInterceptor, WsHandshakeHandler wsHandshakeHandler,
                                        WsProperties wsProperties,
                                        ThreadPoolTaskScheduler brokerTaskScheduler) {
        this.wsHandshakeInterceptor = wsHandshakeInterceptor;
        this.wsHandshakeHandler = wsHandshakeHandler;
        this.wsProperties = wsProperties;
        this.brokerTaskScheduler = brokerTaskScheduler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/bonus")
                .setTaskScheduler(brokerTaskScheduler)
                .setHeartbeatValue(new long[]{
                        wsProperties.getHeartbeatIncoming().toMillis()
                        , wsProperties.getHeartbeatIncoming().toMillis()});
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-endpoint")
                .setAllowedOriginPatterns(wsProperties.getAllowedOrigins().toArray(String[]::new))
                .addInterceptors(wsHandshakeInterceptor)
                .setHandshakeHandler(wsHandshakeHandler);
    }

}
