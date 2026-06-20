package com.github.csifinos.wsendpoint.session;

import java.time.Instant;

public record UserSession(
        String sessionId,
        String userId,
        SessionDetails sessionDetails,
        Instant creationTime,
        Instant lastAccessedTime
) {
}
