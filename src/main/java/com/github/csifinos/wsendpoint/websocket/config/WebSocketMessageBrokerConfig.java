package com.github.csifinos.wsendpoint.websocket.config;

import com.github.csifinos.wsendpoint.session.SessionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private final SessionService sessionService;
    private final WsProperties wsProperties;
    private final ThreadPoolTaskScheduler brokerTaskScheduler;

    public WebSocketMessageBrokerConfig(SessionService sessionService, WsProperties wsProperties,
                                        ThreadPoolTaskScheduler brokerTaskScheduler) {
        this.sessionService = sessionService;
        this.wsProperties = wsProperties;
        this.brokerTaskScheduler = brokerTaskScheduler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue")
                .setTaskScheduler(brokerTaskScheduler)
                .setHeartbeatValue(new long[]{
                        wsProperties.getHeartbeatIncoming().toMillis(),
                        wsProperties.getHeartbeatIncoming().toMillis()});
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/ws-endpoint")
                .setAllowedOriginPatterns(wsProperties.getAllowedOrigins().toArray(String[]::new))
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SessionValidationInterceptor(sessionService));
    }

}
