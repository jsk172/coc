package com.aca.coc.clans.db.repository.raid;

import java.util.List;
import com.aca.coc.clans.db.entity.raid.RaiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RaiderRepository extends JpaRepository<RaiderEntity, Long> {

    @Query("select raider from RaiderEntity raider where raider.tag = :playerTag")
    List<RaiderEntity> findByTag(String playerTag);

    @Query("SELECT raider from RaiderEntity raider where raider.name like CONCAT(:playerName, '%')")
    List<RaiderEntity> findByName(String playerName);

}
