package com.github.csifinos.wsendpoint.websocket.session;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WsSessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsSessionService.class);

    private final WsSessionRepository wsSessionRepository;
    private final WsProperties wsProperties;

    public WsSessionService(WsSessionRepository wsSessionRepository, WsProperties wsProperties) {
        this.wsSessionRepository = wsSessionRepository;
        this.wsProperties = wsProperties;
    }

    public String issueSession() {
        String wsSessionId = UUID.randomUUID().toString();
        long ttlSeconds = wsProperties.getSessionTtl().getSeconds();
        wsSessionRepository.save(new WsSession(wsSessionId, "active", ttlSeconds));
        LOGGER.info("Issued new ws session id: {} with TTL: {} seconds", wsSessionId, ttlSeconds);
        return wsSessionId;
    }

    public boolean isSessionValid(String wsSessionId) {
        if (Strings.isBlank(wsSessionId)) {
            return false;
        }
        LOGGER.info("Validating ws session id: {}", wsSessionId);
        return wsSessionRepository.existsById(wsSessionId);
    }

    public void refreshSession(String wsSessionId) {
        // redis template is faster in this case
        wsSessionRepository.findById(wsSessionId)
                .ifPresent(wsSession -> {
                    wsSession.setTtlSeconds(wsProperties.getSessionTtl().getSeconds());
                    wsSessionRepository.save(wsSession);
                    LOGGER.info("Refreshed ws session id: {} with new TTL: {} seconds", wsSessionId, wsSession.getTtlSeconds());
                });
    }
}

