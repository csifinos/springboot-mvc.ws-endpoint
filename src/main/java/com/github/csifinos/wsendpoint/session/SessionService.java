package com.github.csifinos.wsendpoint.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public SessionService(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Optional<UserSession> getSessionBySessionId(String sessionId) {
        return Optional.ofNullable(sessionRepository.findById(sessionId))
                .map(session -> {
                    String userId = session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
                    SessionDetails sessionDetails = session.getAttribute(SessionConstants.SESSION_DETAILS);

                    UserSession userSession = new UserSession(session.getId(), userId, sessionDetails, session.getCreationTime(), session.getLastAccessedTime());
                    LOGGER.info("Retrieved session {}", userSession);
                    return userSession;
                });
    }

    public List<UserSession> getSessionByUserId(String userId) {
        Map<String, ? extends Session> sessions = sessionRepository.findByPrincipalName(userId);

        List<UserSession> userSessions = sessions
                .values()
                .stream()
                .map(session -> {
                    SessionDetails sessionDetails = session.getAttribute(SessionConstants.SESSION_DETAILS);
                    return new UserSession(session.getId(), userId, sessionDetails, session.getCreationTime(), session.getLastAccessedTime());
                }).toList();

        LOGGER.info("Retrieved sessions {}", userSessions);
        return userSessions;
    }
}
