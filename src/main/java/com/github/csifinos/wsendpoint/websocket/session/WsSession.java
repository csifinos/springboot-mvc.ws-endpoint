package com.github.csifinos.wsendpoint.websocket.session;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("WsSession")
public class WsSession {

    @Id
    private String id;
    private String status;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long ttlSeconds;

    public WsSession() {
    }

    public WsSession(String id, String status, long ttlSeconds) {
        this.id = id;
        this.status = status;
        this.ttlSeconds = ttlSeconds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(Long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }
}

