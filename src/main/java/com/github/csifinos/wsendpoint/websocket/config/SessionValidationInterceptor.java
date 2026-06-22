package com.github.csifinos.wsendpoint.websocket.config;

import com.github.csifinos.wsendpoint.session.SessionService;
import com.github.csifinos.wsendpoint.websocket.presence.PresenceService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

public class SessionValidationInterceptor implements ChannelInterceptor {

    private final SessionService sessionService;
    private final PresenceService presenceService;

    public SessionValidationInterceptor(SessionService sessionService, PresenceService presenceService) {
        this.sessionService = sessionService;
        this.presenceService = presenceService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String httpSessionId = extractHttpSessionId(accessor);
            Principal principal = accessor.getUser();

            if (httpSessionId == null ||
                    principal == null ||
                    !sessionService.isUserOwnerOfSession(principal.getName(), httpSessionId) ||
                    !presenceService.containsPresence(principal.getName())
            ) {
                throw new MessageDeliveryException("Unauthorized: Invalid Session or User contains more than one ws.");
            }
        }
        return message;
    }

    private String extractHttpSessionId(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes == null) {
            throw new MessageDeliveryException("Unauthorized: Invalid Session or User.");
        }
        return (String) sessionAttributes.get(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME);
    }
}
