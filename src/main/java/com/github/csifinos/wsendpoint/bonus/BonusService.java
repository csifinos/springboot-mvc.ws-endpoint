package com.github.csifinos.wsendpoint.bonus;

import com.github.csifinos.wsendpoint.websocket.config.WsProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BonusService {

    private final StringRedisTemplate redisTemplate;
    private final SequenceCounterRepository sequenceCounterRepository;
    private final BonusEventCodec bonusEventCodec;
    private final WsProperties wsProperties;

    public BonusService(StringRedisTemplate redisTemplate,
                        SequenceCounterRepository sequenceCounterRepository,
                        BonusEventCodec bonusEventCodec,
                        WsProperties wsProperties) {
        this.redisTemplate = redisTemplate;
        this.sequenceCounterRepository = sequenceCounterRepository;
        this.bonusEventCodec = bonusEventCodec;
        this.wsProperties = wsProperties;
    }

    public void sendBonusUpdateToSession(AssignBonusDto assignBonusDto, String wsSessionId) {
        SequenceCounter counter = sequenceCounterRepository.findById(wsSessionId)
                .orElse(new SequenceCounter(wsSessionId, 0L));
        long sequence = counter.getValue() + 1;
        counter.setValue(sequence);
        sequenceCounterRepository.save(counter);

        BonusEvent event = new BonusEvent(
                UUID.randomUUID().toString(),
                wsSessionId,
                sequence,
                System.currentTimeMillis(),
                assignBonusDto
        );

//        try {
//            redisTemplate.convertAndSend(wsProperties.getBonusChannel(), bonusEventCodec.encode(event));
//        } catch (Exception ex) {
//            throw new IllegalStateException("Failed to publish bonus event", ex);
//        }
    }
}
