package com.github.csifinos.wsendpoint.websocket;

import com.github.csifinos.wsendpoint.websocket.presence.PresenceService;
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

    public HeartbeatController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @MessageMapping("/heartbeat")
    public void heartbeat(Principal principal, @Header("simpSessionId") String simpSessionId) {
        if (Objects.isNull(principal) || Strings.isBlank(principal.getName()) || Strings.isBlank(simpSessionId)) {
            return;
        }

        String userId = principal.getName();
        presenceService.refresh(userId);
        LOGGER.info("Received heartbeat from userId={} simpSessionId={}", userId, simpSessionId);
    }
}
