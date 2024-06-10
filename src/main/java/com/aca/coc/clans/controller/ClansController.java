package com.aca.coc.clans.controller;

import java.util.List;
import java.util.Map;

import com.aca.coc.clans.service.ClansService;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarRes;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("clans")
public class ClansController {

    private final ClansService clansService;

    @GetMapping("")
    public ResponseEntity<List<ClanRes>> getClans() {
        return ResponseEntity.ok()
                             .body(clansService.getClanResList());
    }

    @GetMapping("/war")
    public ResponseEntity<List<ClanRes>> getClansWar() {
        return ResponseEntity.ok()
                             .body(clansService.getClanWarResList());
    }

    @GetMapping("/capital")
    public ResponseEntity<List<ClanRes>> getClansCapital() {
        return ResponseEntity.ok()
                             .body(clansService.getClanCaptialList());
    }

    @GetMapping("{clanTag}")
    public ResponseEntity<?> findClan(@PathVariable String clanTag) {
        Map<String, Object> clan = clansService.findClanByClanTag(clanTag);
        return ResponseEntity.ok().body(clan);
    }

    @GetMapping("{clanTag}/members")
    public ResponseEntity<ClanMemberListRes> getClanMembers(@PathVariable String clanTag) {
        ClanMemberListRes clanMemberList = clansService.findClanMembersByClanTag(clanTag);
        return ResponseEntity.ok().body(clanMemberList);
    }

    @GetMapping("{clanTag}/current/war")
    public ResponseEntity<ClanCurrentWarRes> getClanCurrentWar(@PathVariable String clanTag) {
        ClanCurrentWarRes clanCurrentWar = clansService.getClanCurrentWar(clanTag);
        return ResponseEntity.ok().body(clanCurrentWar);
    }

    @GetMapping("{clanTag}/capital/raid/seasons")
    public ResponseEntity<ClanCapitalRaidSeasonResponse> getClanCapitalRaidSeasons(@PathVariable String clanTag) {
        ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = clansService.getClanCapitalRaidSeason(clanTag);
        return ResponseEntity.ok().body(clanCapitalRaidAttacker);
    }
}
