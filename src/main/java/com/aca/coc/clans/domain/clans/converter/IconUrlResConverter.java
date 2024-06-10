package com.aca.coc.clans.domain.clans.converter;

import com.aca.coc.clans.domain.clans.IconUrlRes;
import com.aca.coc.external.coc.clan.domain.clan.IconUrl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IconUrlResConverter implements Converter<IconUrl, IconUrlRes> {

    @Override
    public IconUrlRes convert(IconUrl source) {
        return IconUrlRes.builder()
                         .small(source.getSmall())
                         .medium(source.getMedium())
                         .tiny(source.getTiny())
                         .build();
    }

}