package com.github.csifinos.wsendpoint.websocket.pubsub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {

    private static final String CHANNEL_NAME = "websocket-broadcast";

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // Subscribe to a shared broadcast channel
        container.addMessageListener(listenerAdapter, new ChannelTopic(CHANNEL_NAME));
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RedisWebSocketSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber);
    }
}