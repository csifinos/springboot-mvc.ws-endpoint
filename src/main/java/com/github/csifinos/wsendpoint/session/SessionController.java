package com.github.csifinos.wsendpoint.session;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions")
    public String loadGame(Model model, Principal principal, HttpServletRequest request) {
        List<UserSession> sessions = sessionService.getSessionByUserId(principal.getName());
        List<UserSessionDto> userSessions = sessions
                .stream()
                .map(session -> {
                    UserSessionDto userSessionDto = new UserSessionDto();
                    userSessionDto.setSessionId(session.sessionId());
                    userSessionDto.setUserId(session.userId());
                    userSessionDto.setLocation(session.sessionDetails().getLocation());
                    userSessionDto.setAccessType(session.sessionDetails().getAccessType());
                    userSessionDto.setCreationTime(session.creationTime(), request.getLocale());
                    userSessionDto.setLastAccessedTime(session.lastAccessedTime(), request.getLocale());
                    return userSessionDto;
                }).toList();
        model.addAttribute("sessions", userSessions);
        return "sessions";
    }
}
