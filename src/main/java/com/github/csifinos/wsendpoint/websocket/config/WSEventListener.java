package com.github.csifinos.wsendpoint.websocket.config;

import com.github.csifinos.wsendpoint.websocket.presence.PresenceService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WSEventListener {

    private final PresenceService presenceService;

    public WSEventListener(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @Async
    @EventListener
    public void onConnected(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = accessor.getUser();
        String simpSessionId = accessor.getSessionId();

        if (skipInCaseEmptyUserOrSimpSessionId(principal, simpSessionId)) {
            return;
        }

        if (presenceService.containsPresenceWithSimpSessionId(principal.getName(), simpSessionId)) {
            throw new MessageDeliveryException("Not associated WS socket message with user");
        }

        presenceService.register(principal.getName(), simpSessionId);
    }

    @Async
    @EventListener
    public void onDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = accessor.getUser();
        String simpSessionId = accessor.getSessionId();

        if (skipInCaseEmptyUserOrSimpSessionId(principal, simpSessionId)) {
            return;
        }

        if (presenceService.containsPresenceWithSimpSessionId(principal.getName(), simpSessionId)) {
            throw new MessageDeliveryException("Not associated WS socket message with user");
        }

        presenceService.remove(principal.getName());
    }

    private boolean skipInCaseEmptyUserOrSimpSessionId(Principal principal, String simpSessionId) {
        return principal == null || simpSessionId == null;
    }
}
