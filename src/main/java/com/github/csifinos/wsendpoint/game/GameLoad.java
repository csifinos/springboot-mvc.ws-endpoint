package com.github.csifinos.wsendpoint.game;

import com.github.csifinos.wsendpoint.session.UserSession;

public record GameLoad(
        UserSession userSession,
        String userId
) {
}
