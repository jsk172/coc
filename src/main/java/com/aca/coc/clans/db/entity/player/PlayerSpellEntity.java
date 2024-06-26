package com.aca.coc.clans.db.entity.player;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.aca.coc.clans.db.entity.player.common.PlayerItemEntity;
import com.aca.coc.clans.db.entity.player.common.PlayerItemPKEntity;
import com.aca.coc.clans.db.entity.player.common.SpellType;
import org.springframework.data.domain.Persistable;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_player_spell")
public class PlayerSpellEntity implements Persistable<PlayerItemPKEntity> {

    @EmbeddedId
    private PlayerItemPKEntity id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SpellType type;

    @Embedded
    private PlayerItemEntity levelInfo;

    @MapsId(value = "playerTag")
    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_tag", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private PlayerEntity player;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public PlayerItemPKEntity getId() {
        return this.id;
    }

    public void changePlayer(PlayerEntity player) {
        this.player = player;
    }
}
