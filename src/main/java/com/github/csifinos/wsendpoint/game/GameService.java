package com.github.csifinos.wsendpoint.game;

import com.github.csifinos.wsendpoint.websocket.session.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final SessionService sessionService;

    public GameService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public String loadGame() {
        String sessionId = sessionService.issueSession();
        LOGGER.info("Loading game for sessionId: {}", sessionId);
        return sessionId;
    }
}
