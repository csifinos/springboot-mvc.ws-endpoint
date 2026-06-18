package com.github.csifinos.wsendpoint.features.bonus;

public record BonusEvent(
        String messageId,
        long createdAtEpochMs,
        AssignBonusDto payload
) {
}


