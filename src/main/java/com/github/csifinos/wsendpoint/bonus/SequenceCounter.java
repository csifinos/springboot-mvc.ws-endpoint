package com.github.csifinos.wsendpoint.bonus;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("WsSequence")
public class SequenceCounter {

    @Id
    private String wsSessionId;
    private Long value;

    public SequenceCounter() {
    }

    public SequenceCounter(String wsSessionId, long value) {
        this.wsSessionId = wsSessionId;
        this.value = value;
    }

    public String getWsSessionId() {
        return wsSessionId;
    }

    public void setWsSessionId(String wsSessionId) {
        this.wsSessionId = wsSessionId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}

