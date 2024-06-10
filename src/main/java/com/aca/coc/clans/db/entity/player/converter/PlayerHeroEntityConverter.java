package com.aca.coc.clans.db.entity.player.converter;

import com.aca.coc.clans.db.entity.player.PlayerHeroEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemPKEntity;
import com.aca.coc.external.coc.clan.domain.common.Hero;
import org.springframework.stereotype.Component;

@Component
public class PlayerHeroEntityConverter {

    public PlayerHeroEntity convert(String playerTag, Hero source) {
        return PlayerHeroEntity.builder()
                               .id(PlayerItemPKEntity.builder()
                                                     .playerTag(playerTag)
                                                     .name(source.getName())
                                                     .build())
                               .levelInfo(PlayerItemEntity.builder()
                                                          .level(source.getLevel())
                                                          .maxLevel(source.getMaxLevel())
                                                          .build())
                               .build();
    }
}
