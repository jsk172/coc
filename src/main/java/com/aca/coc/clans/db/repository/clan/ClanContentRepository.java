package com.aca.coc.clans.db.repository.clan;

import com.aca.coc.clans.db.entity.clan.ClanContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanContentRepository extends JpaRepository<ClanContentEntity, String> {

}
