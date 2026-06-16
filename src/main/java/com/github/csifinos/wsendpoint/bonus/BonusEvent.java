package com.github.csifinos.wsendpoint.bonus;

public record BonusEvent(
        String messageId,
        long createdAtEpochMs,
        AssignBonusDto payload
) {
}


