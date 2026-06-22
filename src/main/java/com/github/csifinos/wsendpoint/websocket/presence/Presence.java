package com.github.csifinos.wsendpoint.websocket.presence;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("WsPresence")
public class Presence {

    @Id
    private String userId;
    private String simpSessionId;
    private String instanceId;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long ttlSeconds;

    public Presence() {
    }

    public Presence(String userId, String simpSessionId, String instanceId, long ttlSeconds) {
        this.userId = userId;
        this.simpSessionId = simpSessionId;
        this.instanceId = instanceId;
        this.ttlSeconds = ttlSeconds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSimpSessionId() {
        return simpSessionId;
    }

    public void setSimpSessionId(String simpSessionId) {
        this.simpSessionId = simpSessionId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(Long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }
}

