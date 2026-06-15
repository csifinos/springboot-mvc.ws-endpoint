package com.github.csifinos.wsendpoint.websocket.presence;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import com.github.csifinos.wsendpoint.websocket.pubsub.PubSubProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PresenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PresenceService.class);

    private final PresenceRepository presenceRepository;
    private final WsProperties wsProperties;
    private final PubSubProperties pubSubProperties;

    public PresenceService(PresenceRepository presenceRepository,
                           WsProperties wsProperties,
                           PubSubProperties pubSubProperties) {
        this.presenceRepository = presenceRepository;
        this.wsProperties = wsProperties;
        this.pubSubProperties = pubSubProperties;
    }

    public void register(String sessionId, String simpSessionId) {
        long ttlSeconds = wsProperties.getPresenceTtl().getSeconds();
        Presence record = new Presence(simpSessionId, sessionId, pubSubProperties.getInstance(), ttlSeconds);
        presenceRepository.save(record);
        LOGGER.info("Registered websocket presence: sessionId={} simpSessionId={} instanceId={} ttlSeconds={}",
                sessionId, simpSessionId, record.getInstanceId(), ttlSeconds);
    }

    public void refresh(String simpSessionId) {
        // redis template is faster in this case
        presenceRepository.findById(simpSessionId)
                .ifPresent(record -> {
                    record.setTtlSeconds(wsProperties.getPresenceTtl().getSeconds());
                    presenceRepository.save(record);
                    LOGGER.info("Refreshed websocket presence for simpSessionId={}: new ttlSeconds={}",
                            simpSessionId, record.getTtlSeconds());
                });
    }

    public void remove(String simpSessionId) {
        presenceRepository.deleteById(simpSessionId);
        LOGGER.info("Removed websocket presence for simpSessionId={}", simpSessionId);
    }
}
