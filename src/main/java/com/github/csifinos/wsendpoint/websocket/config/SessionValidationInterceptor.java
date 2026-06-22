package com.github.csifinos.wsendpoint.websocket.config;

import com.github.csifinos.wsendpoint.session.SessionService;
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

    public SessionValidationInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String httpSessionId = extractHttpSessionId(accessor);
            Principal principal = accessor.getUser();

            if (httpSessionId == null ||
                    principal == null ||
                    !sessionService.isUserOwnerOfSession(principal.getName(), httpSessionId)) {
                throw new MessageDeliveryException("Unauthorized: Invalid Session or User.");
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
