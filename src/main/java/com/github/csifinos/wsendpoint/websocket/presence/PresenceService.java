package com.github.csifinos.wsendpoint.websocket.presence;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import com.github.csifinos.wsendpoint.websocket.pubsub.PubSubProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void register(String userId, String simpSessionId) {
        long ttlSeconds = wsProperties.getPresenceTtl().getSeconds();
        Presence presence = new Presence(userId, simpSessionId, pubSubProperties.getInstance(), ttlSeconds);
        presenceRepository.save(presence);
        LOGGER.info("Registered websocket presence: userId={} simpSessionId={} instanceId={} ttlSeconds={}",
                userId, simpSessionId, presence.getInstanceId(), ttlSeconds);
    }

    public void refresh(String userId) {
        // redis template is faster in this case
        presenceRepository.findById(userId)
                .ifPresent(presence -> {
                    presence.setTtlSeconds(wsProperties.getPresenceTtl().getSeconds());
                    presenceRepository.save(presence);
                    LOGGER.info("Refreshed websocket presence for userId={}: new ttlSeconds={}",
                            userId, presence.getTtlSeconds());
                });
    }

    public void remove(String userId) {
        presenceRepository.deleteById(userId);
        LOGGER.info("Removed websocket presence for userId={}", userId);
    }

    public Optional<Presence> find(String userId) {
        Optional<Presence> presence = presenceRepository.findById(userId);
        LOGGER.info("Find presence for userId={}:", userId);
        return presence;
    }

    public boolean hasAPresence(String userId) {
        boolean presence = presenceRepository.existsById(userId);
        LOGGER.info("Checked presence for userId={}: exists={}", userId, presence);
        return presence;
    }
}
