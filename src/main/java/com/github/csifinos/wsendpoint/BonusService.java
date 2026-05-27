package com.github.csifinos.wsendpoint;

import jakarta.servlet.http.HttpSession;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class BonusService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public BonusService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendBonusUpdateToSession(AssignBonusDto  assignBonusDto, HttpSession session) {
        simpMessagingTemplate.convertAndSendToUser(session.getId(), "/bonus", assignBonusDto);
    }
}
