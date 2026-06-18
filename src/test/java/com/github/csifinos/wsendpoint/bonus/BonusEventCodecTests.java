package com.github.csifinos.wsendpoint.bonus;

import com.github.csifinos.wsendpoint.features.bonus.AssignBonusDto;
import com.github.csifinos.wsendpoint.features.bonus.BonusEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusEventCodecTests {

    private final BonusEventCodec codec = new BonusEventCodec();

    @Test
    void encodeAndDecodeRoundTrip() {
        AssignBonusDto payload = new AssignBonusDto();
        payload.setPlayerId("p|1");
        payload.setBonusId("b=2");

        BonusEvent original = new BonusEvent("m1", "s1", 7L, 123L, payload);

        String encoded = codec.encode(original);
        BonusEvent decoded = codec.decode(encoded);

        assertEquals(original.messageId(), decoded.messageId());
        assertEquals(original.sessionId(), decoded.sessionId());
        assertEquals(original.sequence(), decoded.sequence());
        assertEquals(original.createdAtEpochMs(), decoded.createdAtEpochMs());
        assertEquals(original.payload().getPlayerId(), decoded.payload().getPlayerId());
        assertEquals(original.payload().getBonusId(), decoded.payload().getBonusId());
    }
}


