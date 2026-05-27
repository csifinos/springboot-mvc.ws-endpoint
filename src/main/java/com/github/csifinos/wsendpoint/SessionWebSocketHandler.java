package com.github.csifinos.wsendpoint;

import jakarta.servlet.http.HttpSession;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
@EnableWebSocketMessageBroker
public class SessionWebSocketHandler implements WebSocketMessageBrokerConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(SessionWebSocketHandler.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/bonus");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-endpoint")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new DefaultHandshakeHandler() {

                    @Override
                    protected @Nullable Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                        HttpSession httpSession = servletRequest.getServletRequest().getSession(false);
                        if (httpSession == null) {
                            return null;
                        }

                        String expected = httpSession.getId();
                        String provided = servletRequest.getServletRequest().getParameter("sessionId");
                        LOG.info("WebSocket connection attempt with sessionId={} (expected {} from cookie)", provided, expected);

                        if (provided == null || !provided.equals(expected)) {
                            return null;
                        }
                        return new SessionPrincipal(expected);
                    }
                });
    }

}
