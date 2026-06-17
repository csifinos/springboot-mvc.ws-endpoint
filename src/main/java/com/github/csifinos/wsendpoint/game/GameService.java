package com.github.csifinos.wsendpoint.game;

import com.github.csifinos.wsendpoint.websocket.session.WsSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final WsSessionService wsSessionService;

    public GameService(WsSessionService wsSessionService) {
        this.wsSessionService = wsSessionService;
    }

    public String loadGame() {
        String wsSession = wsSessionService.issueSession();
        LOGGER.info("Loading game for wsSession: {}", wsSession);
        return wsSession;
    }
}
