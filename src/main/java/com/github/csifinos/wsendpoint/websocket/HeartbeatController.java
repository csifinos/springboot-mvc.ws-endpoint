package com.github.csifinos.wsendpoint.websocket;

import com.github.csifinos.wsendpoint.websocket.presence.PresenceService;
import com.github.csifinos.wsendpoint.websocket.session.WsSessionService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Objects;

@Controller
public class HeartbeatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatController.class);

    private final PresenceService presenceService;
    private final WsSessionService wsSessionService;

    public HeartbeatController(PresenceService presenceService, WsSessionService wsSessionService) {
        this.presenceService = presenceService;
        this.wsSessionService = wsSessionService;
    }

    @MessageMapping("/heartbeat")
    public void heartbeat(Principal principal, @Header("simpSessionId") String simpSessionId) {
        if (Objects.isNull(principal) || Strings.isBlank(principal.getName()) || Strings.isBlank(simpSessionId)) {
            return;
        }

        String sessionId = principal.getName();
        presenceService.refresh(simpSessionId);
        wsSessionService.refreshSession(sessionId);
        LOGGER.info("Received heartbeat from sessionId={} simpSessionId={}", sessionId, simpSessionId);
    }
}
