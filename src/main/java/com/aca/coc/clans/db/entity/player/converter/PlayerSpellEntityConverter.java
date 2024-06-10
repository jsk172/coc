package com.aca.coc.clans.db.entity.player.converter;

import com.aca.coc.clans.db.entity.player.PlayerSpellEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemPKEntity;
import com.aca.coc.clans.db.entity.player.common.Spell;
import com.aca.coc.external.coc.clan.domain.common.Troops;
import org.springframework.stereotype.Component;

@Component
public class PlayerSpellEntityConverter {

    public PlayerSpellEntity convert(String playerTag, Troops source) {
        Spell spell = Spell.findByName(source.getName());
        return PlayerSpellEntity.builder()
                                .id(PlayerItemPKEntity.builder()
                                                      .playerTag(playerTag)
                                                      .name(source.getName())
                                                      .build())
                                .type(spell.getType())
                                .levelInfo(PlayerItemEntity.builder()
                                                           .level(source.getLevel())
                                                           .maxLevel(source.getMaxLevel())
                                                           .build())
                                .build();
    }
}
