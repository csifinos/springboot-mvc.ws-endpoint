package com.github.csifinos.wsendpoint.websocket.presence;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("WsPresence")
public class Presence {

    @Id
    private String simpSessionId;
    private String sessionId;
    private String instanceId;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long ttlSeconds;

    public Presence() {
    }

    public Presence(String simpSessionId, String sessionId, String instanceId, long ttlSeconds) {
        this.simpSessionId = simpSessionId;
        this.sessionId = sessionId;
        this.instanceId = instanceId;
        this.ttlSeconds = ttlSeconds;
    }

    public String getSimpSessionId() {
        return simpSessionId;
    }

    public void setSimpSessionId(String simpSessionId) {
        this.simpSessionId = simpSessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

