package com.github.csifinos.wsendpoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game")
    public String loadGame(HttpServletRequest request, Model model) {
        HttpSession session = gameService.loadGame(request);
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("createdAt", session.getCreationTime());
        LOG.info("Loaded game for session {}", session.getId());
        return "game";
    }
}
