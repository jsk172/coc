package com.aca.coc.clans.domain.clans.converter;

import com.aca.coc.clans.domain.clans.ClanCapitalRaidSeasonMemberResponse;
import com.aca.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasonMember;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClanCapitalRaidSeasonMemberResponseConverter implements Converter<ClanCapitalRaidSeasonMember, ClanCapitalRaidSeasonMemberResponse> {

    @Override
    public ClanCapitalRaidSeasonMemberResponse convert(ClanCapitalRaidSeasonMember source) {
        return ClanCapitalRaidSeasonMemberResponse.builder()
                                                  .tag(source.getTag())
                                                  .name(source.getName())
                                                  .attacks(source.getAttacks())
                                                  .attackLimit(source.getAttackLimit())
                                                  .bonusAttackLimit(source.getBonusAttackLimit())
                                                  .capitalResourcesLooted(source.getCapitalResourcesLooted())
                                                  .build();
    }

}
