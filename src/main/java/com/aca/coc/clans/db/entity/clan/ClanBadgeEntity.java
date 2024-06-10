package com.aca.coc.clans.db.entity.clan;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.aca.coc.clans.db.entity.common.IconUrlEntity;
import org.springframework.data.domain.Persistable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_clan_badge")
public class ClanBadgeEntity implements Persistable<String> {

    @Id
    @Column(name = "tag", length = 100)
    private String tag;

    @Embedded
    private IconUrlEntity iconUrl;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.tag;
    }

}
