package com.github.csifinos.wsendpoint.websocket.config;

import com.github.csifinos.wsendpoint.bonus.BonusRedisSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class WsConfig {

    @Bean
    public ThreadPoolTaskScheduler brokerTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("ws-heartbeat-");
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.ws", name = "redis-pubsub-enabled", havingValue = "true", matchIfMissing = true)
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       BonusRedisSubscriber bonusRedisSubscriber,
                                                                       WsProperties wsProperties) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(bonusRedisSubscriber, new PatternTopic(wsProperties.getBonusChannel()));
        return container;
    }
}



