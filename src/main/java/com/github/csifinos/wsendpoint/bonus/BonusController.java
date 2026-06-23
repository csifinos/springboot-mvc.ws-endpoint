package com.github.csifinos.wsendpoint.bonus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @PostMapping("/api/v1/bonus")
    public void assignBonus(@RequestBody AssignBonusDto assignBonusDto) {
        bonusService.sendBonusUpdateToSession(assignBonusDto);
    }
}
