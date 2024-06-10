package com.aca.coc.clans.db.entity.raid.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.aca.coc.clans.db.entity.raid.RaidEntity;
import com.aca.coc.clans.db.entity.raid.RaiderEntity;
import com.aca.coc.clans.domain.raid.ClanCapitalRaidSeasonMemberResponse;
import com.aca.coc.clans.domain.raid.ClanCapitalRaidSeasonResponse;
import com.aca.coc.clans.domain.clans.converter.TimeConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class RaidEntityConverter {
    private final TimeConverter timeConverter;
    private final RaiderEntityConverter raiderEntityConverter;

    public RaidEntity convert(String clanTag, ClanCapitalRaidSeasonResponse source) {
        return RaidEntity.builder()
                         .clanTag(clanTag)
                         .startDate(timeConverter.toLocalDate(source.getStartTime()))
                         .endDate(timeConverter.toLocalDate(source.getEndTime()))
                         .radierEntityList(makeMembers(source.getMembers())).build();
    }

    private List<RaiderEntity> makeMembers(List<ClanCapitalRaidSeasonMemberResponse> members) {
        if (CollectionUtils.isEmpty(members)) return Collections.emptyList();

        return members.stream()
                .map(raiderEntityConverter::convert)
                .collect(Collectors.toList());
    }
}
