package com.github.csifinos.wsendpoint;

import java.security.Principal;

public class SessionPrincipal implements Principal {
    private final String sessionId;

    public SessionPrincipal(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getName() {
        return sessionId;
    }
}
