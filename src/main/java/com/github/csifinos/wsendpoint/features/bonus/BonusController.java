package com.github.csifinos.wsendpoint.features.bonus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @PostMapping("/v1/bonus")
    public void assignBonus(@RequestParam String wsSessionId,
                            @RequestBody AssignBonusDto assignBonusDto) {
        bonusService.sendBonusUpdateToSession(assignBonusDto, wsSessionId);
    }
}
