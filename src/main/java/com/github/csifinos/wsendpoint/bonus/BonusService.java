package com.github.csifinos.wsendpoint.bonus;

import com.github.csifinos.wsendpoint.websocket.DestinationService;
import com.github.csifinos.wsendpoint.websocket.presence.Presence;
import com.github.csifinos.wsendpoint.websocket.presence.PresenceService;
import com.github.csifinos.wsendpoint.websocket.pubsub.RedisPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class BonusService {

    private static final String BONUS_TOPIC = "bonus";

    private final PresenceService presenceService;
    private final RedisPublisher redisPublisher;
    private final DestinationService destinationService;

    public BonusService(PresenceService presenceService, RedisPublisher redisPublisher, DestinationService destinationService) {
        this.presenceService = presenceService;
        this.redisPublisher = redisPublisher;
        this.destinationService = destinationService;
    }

    public void sendBonusUpdateToSession(AssignBonusDto assignBonusDto) {
        Presence presence = presenceService.find(assignBonusDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("Presence not found for userId: " + assignBonusDto.getUserId()));

        BonusEvent event = new BonusEvent(
                UUID.randomUUID().toString(),
                Instant.now(Clock.systemUTC()).getEpochSecond(),
                assignBonusDto
        );

        redisPublisher.publish(destinationService.getDestination(), presence.getSimpSessionId(), BONUS_TOPIC, event);
    }
}
