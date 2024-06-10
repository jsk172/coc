package com.aca.coc.clans.db.repository.clan;

import java.util.List;
import com.aca.coc.clans.db.entity.clan.ClanAssignedPlayerPKEntity;
import com.aca.coc.clans.db.entity.clan.ClanLeagueAssignedPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanLeagueAssignedPlayerRepository extends JpaRepository<ClanLeagueAssignedPlayerEntity, ClanAssignedPlayerPKEntity> {

    @Query("select max(clap.id.seasonDate) from ClanLeagueAssignedPlayerEntity clap")
    String findLatestLeagueSeasonDate();

    @Query("select clap from ClanLeagueAssignedPlayerEntity clap where clap.id.seasonDate = :seasonDate and clap.clan.tag = :clanTag")
    List<ClanLeagueAssignedPlayerEntity> findClanLeagueAssignedPlayersByClanTagAndSeasonDate(String clanTag, String seasonDate);

    @Query("select clap from ClanLeagueAssignedPlayerEntity clap where clap.id.seasonDate = :seasonDate")
    List<ClanLeagueAssignedPlayerEntity> findBySeasonDate(String seasonDate);

    @Modifying
    @Query("delete from ClanLeagueAssignedPlayerEntity clap where clap.id.seasonDate = :seasonDate")
    void deleteAllBySeasonDate(String seasonDate);

}
