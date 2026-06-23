package com.github.csifinos.wsendpoint.websocket.config;

import com.github.csifinos.wsendpoint.websocket.presence.PresenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class PresenceRefreshInterceptor implements ChannelInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PresenceRefreshInterceptor.class);

    private final PresenceService presenceService;

    public PresenceRefreshInterceptor(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);


        if (accessor != null && accessor.getUser() != null) {
            String userTd = accessor.getUser().getName();
            presenceService.refresh(userTd);
            LOGGER.info("Received heartbeat from userId={}", userTd);
        }
        return message;
    }
}
