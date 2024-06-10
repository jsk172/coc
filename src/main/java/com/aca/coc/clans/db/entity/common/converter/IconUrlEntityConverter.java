package com.aca.coc.clans.db.entity.common.converter;

import com.aca.coc.clans.db.entity.common.IconUrlEntity;
import com.aca.coc.external.coc.clan.domain.common.IconUrl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IconUrlEntityConverter implements Converter<IconUrl, IconUrlEntity> {

    @Override
    public IconUrlEntity convert(IconUrl source) {
        return IconUrlEntity.builder()
                            .tiny(source.getTiny())
                            .small(source.getSmall())
                            .medium(source.getMedium())
                            .large(source.getLarge())
                            .build();
    }

}
