package com.github.csifinos.wsendpoint.websocket.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class RedisPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisPublisher.class);
    private static final String CHANNEL_NAME = "websocket-broadcast";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisPublisher(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(String destination, Object payload) {
        Message message = new Message(destination, payload);
        String jsonMessage = objectMapper.writeValueAsString(message);
        redisTemplate.convertAndSend(CHANNEL_NAME, jsonMessage);
        LOGGER.info("Published message to Redis channel: destination={} payload={} at channel={}", destination, payload, CHANNEL_NAME);
    }
}
