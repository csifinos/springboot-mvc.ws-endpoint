package com.github.csifinos.wsendpoint;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @PostMapping("/v1/bonus")
    public void assignBonus(@RequestBody AssignBonusDto assignBonusDto, HttpSession session) {
        bonusService.sendBonusUpdateToSession(assignBonusDto, session);
    }
}
