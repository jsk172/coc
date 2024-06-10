package com.aca.coc.external.coc.clan;

import java.util.Map;
import java.util.Optional;
import com.aca.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import com.aca.coc.external.coc.clan.domain.clan.ClanMemberList;
import com.aca.coc.external.coc.clan.domain.clan.ClanWar;
import com.aca.coc.external.coc.clan.domain.player.Player;
import org.springframework.stereotype.Component;

@Component
public interface ClanApiService {

    Map<String, Object> findClanByClanTag(String clanTag);

    Optional<ClanCapitalRaidSeasons> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit);

    Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag);

    Optional<ClanMemberList> findClanMembersByClanTag(String clanTag);

    Optional<Player> findPlayerBy(String playerTag);

}
