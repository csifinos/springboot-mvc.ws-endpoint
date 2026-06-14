package com.github.csifinos.wsendpoint.websocket.session;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;
    private final WsProperties wsProperties;

    public SessionService(@Qualifier("WsSessionRepository") SessionRepository sessionRepository, WsProperties wsProperties) {
        this.sessionRepository = sessionRepository;
        this.wsProperties = wsProperties;
    }

    public String issueSession() {
        String sessionId = UUID.randomUUID().toString();
        long ttlSeconds = wsProperties.getSessionTtl().getSeconds();
        sessionRepository.save(new Session(sessionId, "active", ttlSeconds));
        LOGGER.info("Issued new ws session id: {} with TTL: {} seconds", sessionId, ttlSeconds);
        return sessionId;
    }

    public boolean isSessionValid(String wsSessionId) {
        if (Strings.isBlank(wsSessionId)) {
            return false;
        }
        LOGGER.info("Validating ws session id: {}", wsSessionId);
        return sessionRepository.existsById(wsSessionId);
    }

    public void refreshSession(String wsSessionId) {
        // redis template is faster in this case
        sessionRepository.findById(wsSessionId)
                .ifPresent(session -> {
                    session.setTtlSeconds(wsProperties.getSessionTtl().getSeconds());
                    sessionRepository.save(session);
                    LOGGER.info("Refreshed ws session id: {} with new TTL: {} seconds", wsSessionId, session.getTtlSeconds());
                });
    }
}

