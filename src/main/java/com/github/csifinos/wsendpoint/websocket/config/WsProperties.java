package com.github.csifinos.wsendpoint.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "app.ws")
public class WsProperties {

    private List<String> allowedOrigins;
    private String brokerURL;
    private Duration heartbeatIncoming;
    private Duration heartbeatOutgoing;
    private Duration reconnectDelay;
    private Duration sessionTtl;
    private Duration presenceTtl;
    private Duration refreshInterval;

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public Duration getHeartbeatIncoming() {
        return heartbeatIncoming;
    }

    public void setHeartbeatIncoming(Duration heartbeatIncoming) {
        this.heartbeatIncoming = heartbeatIncoming;
    }

    public Duration getHeartbeatOutgoing() {
        return heartbeatOutgoing;
    }

    public void setHeartbeatOutgoing(Duration heartbeatOutgoing) {
        this.heartbeatOutgoing = heartbeatOutgoing;
    }

    public Duration getReconnectDelay() {
        return reconnectDelay;
    }

    public void setReconnectDelay(Duration reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }

    public Duration getSessionTtl() {
        return sessionTtl;
    }

    public void setSessionTtl(Duration sessionTtl) {
        this.sessionTtl = sessionTtl;
    }

    public Duration getPresenceTtl() {
        return presenceTtl;
    }

    public void setPresenceTtl(Duration presenceTtl) {
        this.presenceTtl = presenceTtl;
    }

    public Duration getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Duration refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}


