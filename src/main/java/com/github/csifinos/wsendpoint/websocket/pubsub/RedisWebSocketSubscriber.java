package com.github.csifinos.wsendpoint.websocket.pubsub;

import com.github.csifinos.wsendpoint.websocket.DestinationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class RedisWebSocketSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisWebSocketSubscriber.class);

    private final DestinationService destinationService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public RedisWebSocketSubscriber(DestinationService destinationService, SimpMessagingTemplate messagingTemplate,
                                    ObjectMapper objectMapper) {
        this.destinationService = destinationService;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    public void handleMessage(String json) {
        Message msg = objectMapper.readValue(json, Message.class);
        LOGGER.info("Received message from Redis channel: destination={} payload={}", msg.getDestination(), msg.getPayload());

        if (destinationService.isDestinationThisInstance(msg.getDestination())) {
            messagingTemplate.convertAndSend(msg.getDestination(), msg.getPayload());
            LOGGER.info("Broadcasted message to local clients: destination={} payload={}", msg.getDestination(), msg.getPayload());
        }
    }
}
