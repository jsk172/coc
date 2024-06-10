package com.aca.coc.clans.domain.clans.converter;

import lombok.RequiredArgsConstructor;
import com.aca.coc.clans.domain.clans.LeagueRes;
import com.aca.coc.external.coc.clan.domain.clan.League;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeagueResConverter implements Converter<League, LeagueRes> {

    private final IconUrlResConverter iconUrlResConverter;

    @Override
    public LeagueRes convert(League source) {
        return LeagueRes.builder()
                        .id(source.getId())
                        .name(source.getName())
                        .iconUrls(iconUrlResConverter.convert(source.getIconUrls()))
                        .build();
    }

}