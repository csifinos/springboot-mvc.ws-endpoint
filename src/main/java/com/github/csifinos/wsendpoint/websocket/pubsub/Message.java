package com.github.csifinos.wsendpoint.websocket.pubsub;

public class Message {
    private final String destination;
    private final String sessionId;
    private final String topic;
    private final Object payload;

    public Message(String destination, String sessionId, String topic, Object payload) {
        this.destination = destination;
        this.sessionId = sessionId;
        this.topic = topic;
        this.payload = payload;
    }


    public String getDestination() {
        return destination;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getTopic() {
        return topic;
    }

    public Object getPayload() {
        return payload;
    }
}
