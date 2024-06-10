package com.aca.coc.clans.db.entity.player.converter;

import com.aca.coc.clans.db.entity.common.YnType;
import com.aca.coc.clans.db.entity.player.PlayerHeroEquipmentEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemPKEntity;
import com.aca.coc.external.coc.clan.domain.common.HeroEquipment;
import com.aca.coc.external.coc.config.HeroEquipmentConfig;
import org.springframework.stereotype.Component;

@Component
public class PlayerHeroEquipmentEntityConverter {

    public PlayerHeroEquipmentEntity convert(String playerTag, HeroEquipment source) {

        HeroEquipmentConfig heroEquipmentConfig = HeroEquipmentConfig.findByName(source.getName());

        return PlayerHeroEquipmentEntity.builder()
                                        .id(PlayerItemPKEntity.builder()
                                                              .playerTag(playerTag)
                                                              .name(source.getName())
                                                              .build())
                                        .levelInfo(PlayerItemEntity.builder()
                                                                   .level(source.getLevel())
                                                                   .maxLevel(source.getMaxLevel())
                                                                   .build())
                                        .targetHeroName(heroEquipmentConfig.getHero().getName())
                                        .wearYn(YnType.N)
                                        .build();
    }
}
