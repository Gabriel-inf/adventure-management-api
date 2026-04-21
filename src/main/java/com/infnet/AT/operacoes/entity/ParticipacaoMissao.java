package com.infnet.AT.operacoes.entity;

import com.infnet.AT.operacoes.entity.Enum.PapelMissao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(schema = "operacoes", name = "participacao_missao")
@Getter@Setter
public class ParticipacaoMissao {
    @EmbeddedId
    private ParticipacaoMissaoId id = new ParticipacaoMissaoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("missaoId")
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("aventureiroId")
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false, length = 40)
    private PapelMissao papel;

    @Column(name = "recompensa_ouro", precision = 10, scale = 2)
    private BigDecimal recompensaOuro = BigDecimal.ZERO;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque=false;

    @CreationTimestamp
    @Column(name = "data_registro", nullable = false)
    private OffsetDateTime dataRegistro;
}
