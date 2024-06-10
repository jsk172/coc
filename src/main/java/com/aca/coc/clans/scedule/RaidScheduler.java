package com.aca.coc.clans.scedule;

import com.aca.coc.clans.service.RaidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RaidScheduler {
    private final RaidService raidService;

    @Scheduled(cron = "0 0 12 ? * FRI")
    public void raidScheduling() {
        raidService.collectClanCapitalRaidSeason();
    }
}
