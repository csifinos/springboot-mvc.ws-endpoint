package com.github.csifinos.wsendpoint.websocket;

import com.github.csifinos.wsendpoint.websocket.pubsub.PubSubProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Service
public class DestinationService {

    private static final String DESTINATION_PATTERN = "/%s/%s";
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    private final String destination;

    public DestinationService(PubSubProperties pubSubProperties) {
        this.destination = String.format(DESTINATION_PATTERN,
                pubSubProperties.getNamespace(), pubSubProperties.getInstance());
    }

    public String getDestination() {
        return destination;
    }

    public boolean isDestinationThisInstance(String destination) {
        return MATCHER.match(this.destination, destination);
    }
}
