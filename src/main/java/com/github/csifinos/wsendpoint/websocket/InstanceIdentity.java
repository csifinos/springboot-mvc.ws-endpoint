package com.github.csifinos.wsendpoint.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InstanceIdentity {

    private final String instanceId;

    public InstanceIdentity(@Value("${HOSTNAME:ws-endpoint}") String hostName) {
        this.instanceId = hostName + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getInstanceId() {
        return instanceId;
    }
}


