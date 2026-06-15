package com.github.csifinos.wsendpoint.websocket.pubsub;

public class Message {
    private String destination;
    private Object payload;

    public Message(String destination, Object payload) {
        this.destination = destination;
        this.payload = payload;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
