package com.aca.coc.clans.domain.clans.converter;

import lombok.RequiredArgsConstructor;
import com.aca.coc.clans.domain.clans.ClanMemberRes;
import com.aca.coc.external.coc.clan.domain.clan.ClanMember;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanMemberResConverter implements Converter<ClanMember, ClanMemberRes> {


    private final LeagueResConverter leagueResConverter;

    @Override
    public ClanMemberRes convert(ClanMember source) {
        return ClanMemberRes.builder()
                            .name(source.getName())
                            .tag(source.getTag())
                            .role(source.getRole())
                            .townHallLevel(source.getTownHallLevel())
                            .expLevel(source.getExpLevel())
                            .trophies(source.getTrophies())
                            .clanRank(source.getClanRank())
                            .previousClanRank(source.getPreviousClanRank())
                            .donations(source.getDonations())
                            .donationsReceived(source.getDonationsReceived())
                            .league(leagueResConverter.convert(source.getLeague()))
                            .build();
    }

}