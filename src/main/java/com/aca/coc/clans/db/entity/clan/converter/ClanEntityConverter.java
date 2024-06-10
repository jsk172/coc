package com.aca.coc.clans.db.entity.clan.converter;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import com.aca.coc.clans.db.entity.clan.ClanEntity;
import com.aca.coc.clans.db.entity.common.YnType;
import com.aca.coc.external.coc.clan.domain.common.PlayerClan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanEntityConverter implements Converter<PlayerClan, ClanEntity> {

    @Override
    public @NonNull ClanEntity convert(PlayerClan source) {
        return ClanEntity.builder()
                         .tag(source.getTag())
                         .name(source.getName())
                         .order(Integer.MAX_VALUE)
                         .visibleYn(YnType.N)
                         .regDate(LocalDateTime.now())
                         .build();
    }

}
