package com.github.csifinos.wsendpoint.bonus;

public record BonusEvent(
        String messageId,
        String sessionId,
        long createdAtEpochMs,
        AssignBonusDto payload
) {
}


