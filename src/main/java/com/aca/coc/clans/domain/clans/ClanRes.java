package com.aca.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.aca.coc.clans.common.Clan;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanRes {

    private String name;
    private String tag;

    public static ClanRes create(Clan clan) {
        return ClanRes.builder()
                      .name(clan.getName())
                      .tag(clan.getTag())
                      .build();
    }

}
