package com.github.csifinos.wsendpoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public HttpSession loadGame(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        if (session.isNew()) {
            return session;
        }
        session.invalidate();
        return request.getSession(true);
    }
}
