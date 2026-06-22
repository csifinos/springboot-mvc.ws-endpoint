package com.github.csifinos.wsendpoint.game;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.time.ZoneOffset;

@Controller
public class GameController {

    private final WsProperties wsProperties;
    private final GameService gameService;

    public GameController(WsProperties wsProperties, GameService gameService) {
        this.wsProperties = wsProperties;
        this.gameService = gameService;
    }

    @GetMapping("/game")
    public String loadGame(Model model, HttpSession httpSession) {
        GameLoad gameLoad = gameService.loadGame(httpSession);
        model.addAttribute("userSession", gameLoad.userSession());
        model.addAttribute("brokerUrl", wsProperties.getBrokerURL());
        model.addAttribute("reconnectDelay", wsProperties.getReconnectDelay().toMillis());
        model.addAttribute("heartbeatIncoming", wsProperties.getHeartbeatIncoming().toMillis());
        model.addAttribute("heartbeatOutgoing", wsProperties.getHeartbeatOutgoing().toMillis());
        model.addAttribute("refreshInterval", wsProperties.getRefreshInterval().toMillis());
        model.addAttribute("createdAt", Instant.now().atOffset(ZoneOffset.UTC));
        return "game";
    }
}
