package com.github.csifinos.wsendpoint.game;

import com.github.csifinos.wsendpoint.session.SessionService;
import com.github.csifinos.wsendpoint.session.UserSession;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final SessionService sessionService;

    public GameService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public GameLoad loadGame(HttpSession httpSession, Principal principal) {
        UserSession userSession = sessionService.getSessionBySessionId(httpSession.getId())
                .orElseThrow(() -> new RuntimeException("No session found"));
        GameLoad gameLoad = new GameLoad(userSession, principal.getName());
        LOGGER.info("Loading game with details: {}", gameLoad);
        return gameLoad;
    }
}
