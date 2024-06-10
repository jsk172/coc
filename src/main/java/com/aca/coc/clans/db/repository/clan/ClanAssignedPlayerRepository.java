package com.aca.coc.clans.db.repository.clan;

import java.util.List;
import com.aca.coc.clans.db.entity.clan.ClanAssignedPlayerEntity;
import com.aca.coc.clans.db.entity.clan.ClanAssignedPlayerPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanAssignedPlayerRepository extends JpaRepository<ClanAssignedPlayerEntity, ClanAssignedPlayerPKEntity> {

    @Query("select max(cap.id.seasonDate) from ClanAssignedPlayerEntity cap")
    String findLatestSeasonDate();

    @Query("select cap from ClanAssignedPlayerEntity cap where cap.id.seasonDate = :seasonDate and cap.clan.tag = :clanTag")
    List<ClanAssignedPlayerEntity> findClanAssignedPlayersByClanTagAndSeasonDate(String clanTag, String seasonDate);

    @Query("select cap from ClanAssignedPlayerEntity cap where cap.id.seasonDate = :seasonDate")
    List<ClanAssignedPlayerEntity> findBySeasonDate(String seasonDate);

    @Modifying
    @Query("delete from ClanAssignedPlayerEntity cape where cape.id.seasonDate = :seasonDate")
    void deleteAllBySeasonDate(String seasonDate);

}
