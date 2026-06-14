package com.github.csifinos.wsendpoint.bonus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class BonusRedisSubscriber implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(BonusRedisSubscriber.class);

    private final BonusEventCodec bonusEventCodec;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public BonusRedisSubscriber(BonusEventCodec bonusEventCodec,
                                SimpMessagingTemplate simpMessagingTemplate,
                                SimpUserRegistry simpUserRegistry) {
        this.bonusEventCodec = bonusEventCodec;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);

        try {
            BonusEvent event = bonusEventCodec.decode(body);
            if (simpUserRegistry.getUser(event.sessionId()) == null) {
                return;
            }

            simpMessagingTemplate.convertAndSendToUser(event.sessionId(), "/bonus", event);
        } catch (Exception ex) {
            LOG.error("Failed to process redis bonus event", ex);
        }
    }
}



