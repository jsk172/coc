package com.aca.coc.clans.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.aca.coc.clans.db.entity.league.LeagueEntity;
import com.aca.coc.clans.db.repository.common.LeagueRepository;
import com.aca.coc.clans.domain.clans.LabelResponse;
import com.aca.coc.clans.domain.clans.converter.LabelResponseConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaguesService {

    private final LeagueRepository leagueRepository;
    private final LabelResponseConverter labelResponseConverter;

    public List<LabelResponse> getLeagues() {
        List<LeagueEntity> leagueEntities = leagueRepository.findAll();

        return leagueEntities.stream()
                             .map(labelResponseConverter::convert)
                             .collect(Collectors.toList());
    }
}
