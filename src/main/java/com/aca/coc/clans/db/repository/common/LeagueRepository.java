package com.aca.coc.clans.db.repository.common;

import com.aca.coc.clans.db.entity.league.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<LeagueEntity, Integer> {
}
