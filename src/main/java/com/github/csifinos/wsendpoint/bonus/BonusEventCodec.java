package com.github.csifinos.wsendpoint.bonus;

import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class BonusEventCodec {

    public String encode(BonusEvent event) {
        return join(
                event.messageId(),
                event.sessionId(),
                String.valueOf(event.sequence()),
                String.valueOf(event.createdAtEpochMs()),
                event.payload().getPlayerId(),
                event.payload().getBonusId()
        );
    }

    public BonusEvent decode(String value) {
        String[] tokens = value.split("\\|", -1);
        if (tokens.length != 6) {
            throw new IllegalArgumentException("Invalid bonus event payload");
        }

        AssignBonusDto payload = new AssignBonusDto();
        payload.setPlayerId(decodeToken(tokens[4]));
        payload.setBonusId(decodeToken(tokens[5]));

        return new BonusEvent(
                decodeToken(tokens[0]),
                decodeToken(tokens[1]),
                Long.parseLong(tokens[2]),
                Long.parseLong(tokens[3]),
                payload
        );
    }

    private String join(String... values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append('|');
            }
            builder.append(encodeToken(values[i] == null ? "" : values[i]));
        }
        return builder.toString();
    }

    private String encodeToken(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String decodeToken(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}


