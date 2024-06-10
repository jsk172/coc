package com.aca.coc.clans.db.repository.player;

import com.aca.coc.clans.db.entity.player.PlayerHeroEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerHeroRepository extends JpaRepository<PlayerHeroEntity, PlayerItemPKEntity> {
}
