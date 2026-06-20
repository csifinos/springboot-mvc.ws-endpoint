package com.github.csifinos.wsendpoint.websocket.session;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WsSessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsSessionService.class);

    private final WsSessionRepository wsSessionRepository;
    private final HttpSession httpSession;
    private final WsProperties wsProperties;

    public WsSessionService(WsSessionRepository wsSessionRepository, HttpSession httpSession, WsProperties wsProperties) {
        this.wsSessionRepository = wsSessionRepository;
        this.httpSession = httpSession;
        this.wsProperties = wsProperties;
    }

    public String issueSession() {
        String wsSessionId = UUID.randomUUID().toString();
        String userId = httpSession.getId();
        long ttlSeconds = wsProperties.getSessionTtl().getSeconds();

        wsSessionRepository.save(new WsSession(wsSessionId, userId, ttlSeconds));
        LOGGER.info("Issued new ws session id: {} for user {}, with TTL: {} seconds", wsSessionId, userId, ttlSeconds);
        return wsSessionId;
    }

//    public boolean hasUserAnotherWsSession() {
//        String userId = httpSession.getId();
//        wsSessionRepository.
//    }

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

