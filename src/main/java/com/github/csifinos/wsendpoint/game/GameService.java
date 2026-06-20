package com.github.csifinos.wsendpoint.game;

import com.github.csifinos.wsendpoint.session.SessionService;
import jakarta.servlet.http.HttpSession;
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

    public GameLoad loadGame(HttpSession httpSession) {
//        String wsSession = wsSessionService.issueSession();
        GameLoad gameLoad = new GameLoad(
                sessionService.getSessionBySessionId(httpSession.getId()),
                null);
        LOGGER.info("Loading game with details: {}", gameLoad);
        return gameLoad;
    }
}
