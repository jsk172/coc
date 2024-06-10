package com.aca.coc.clans.db.entity.raid.converter;

import com.aca.coc.clans.db.entity.raid.RaiderEntity;
import com.aca.coc.clans.domain.raid.ClanCapitalRaidSeasonMemberResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RaiderEntityConverter implements Converter<ClanCapitalRaidSeasonMemberResponse, RaiderEntity> {

    @Override
    public RaiderEntity convert(ClanCapitalRaidSeasonMemberResponse source) {
        return RaiderEntity.builder()
                           .tag(source.getTag())
                           .name(source.getName())
                           .attacks(source.getAttacks())
                           .resourceLooted(source.getCapitalResourcesLooted()).build();
    }
}
