package com.aca.coc.clans.db.entity.league.converter;

import lombok.RequiredArgsConstructor;
import com.aca.coc.clans.db.entity.common.converter.IconUrlEntityConverter;
import com.aca.coc.clans.db.entity.league.LeagueEntity;
import com.aca.coc.external.coc.clan.domain.common.Label;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeagueEntityConverter implements Converter<Label, LeagueEntity> {

    private final IconUrlEntityConverter iconUrlEntityConverter;

    @Override
    public @NonNull LeagueEntity convert(Label source) {
        return LeagueEntity.builder()
                           .id(source.getId())
                           .name(source.getName())
                           .iconUrl(iconUrlEntityConverter.convert(source.getIconUrls()))
                           .build();
    }

}
