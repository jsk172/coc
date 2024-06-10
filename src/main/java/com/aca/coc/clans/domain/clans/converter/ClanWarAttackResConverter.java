package com.aca.coc.clans.domain.clans.converter;

import com.aca.coc.clans.domain.clans.ClanWarAttackRes;
import com.aca.coc.external.coc.clan.domain.clan.ClanWarAttack;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClanWarAttackResConverter implements Converter<ClanWarAttack, ClanWarAttackRes> {

    @Override
    public ClanWarAttackRes convert(ClanWarAttack source) {
        return ClanWarAttackRes.builder()
                               .attackerTag(source.getAttackerTag())
                               .defenderTag(source.getDefenderTag())
                               .destructionPercentage(source.getDestructionPercentage())
                               .duration(source.getDuration())
                               .stars(source.getStars())
                               .order(source.getOrder())
                               .build();

    }

}