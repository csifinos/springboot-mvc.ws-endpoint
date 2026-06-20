package com.github.csifinos.wsendpoint.websocket.session;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("WsSession")
public class WsSession {

    @Id
    private String id;
    private String userId;
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long ttlSeconds;

    public WsSession() {}

    public WsSession(String id, String userId, long ttlSeconds) {
        this.id = id;
        this.userId = userId;
        this.ttlSeconds = ttlSeconds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTtlSeconds(Long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Long getTtlSeconds() {
        return ttlSeconds;
    }
}

