package com.github.csifinos.wsendpoint.websocket;

import com.github.csifinos.wsendpoint.websocket.pubsub.PubSubProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Service
public class DestinationService {

    private static final String DESTINATION_PATTERN = "/%s/%s/%s/%s";
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    private final PubSubProperties pubSubProperties;

    public DestinationService(PubSubProperties pubSubProperties) {
        this.pubSubProperties = pubSubProperties;
    }

    public String constructDestination(String wsSessionId, String topic) {
        return String.format(DESTINATION_PATTERN,
                pubSubProperties.getNamespace(), pubSubProperties.getInstance(),
                wsSessionId, topic);
    }

    public boolean isDestinationThisInstance(String destination) {
        String currentDestination = String.format(DESTINATION_PATTERN,
                pubSubProperties.getNamespace(), pubSubProperties.getInstance(),
                "*", "*");
        return MATCHER.match(currentDestination, destination);
    }
}
