package com.github.csifinos.wsendpoint.bonus;

import com.github.csifinos.wsendpoint.websocket.DestinationService;
import com.github.csifinos.wsendpoint.websocket.pubsub.RedisPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BonusService {

    private static final String BONUS_TOPIC = "bonus";

    private final RedisPublisher redisPublisher;
    private final DestinationService destinationService;

    public BonusService(RedisPublisher redisPublisher, DestinationService destinationService) {
        this.redisPublisher = redisPublisher;
        this.destinationService = destinationService;
    }

    public void sendBonusUpdateToSession(AssignBonusDto assignBonusDto, String wsSessionId) {
        BonusEvent event = new BonusEvent(
                UUID.randomUUID().toString(),
                wsSessionId,
                System.currentTimeMillis(),
                assignBonusDto
        );

        redisPublisher.publish(destinationService.constructDestination(wsSessionId, BONUS_TOPIC), event);
    }
}
