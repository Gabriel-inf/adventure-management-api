package com.infnet.AT.operacoes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Immutable
@Table(schema = "operacoes", name = "vw_painel_tatico_missao")
@Getter @Setter
public class PainelTaticoMissao {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "nivel_perigo", length = 20)
    private String nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Long totalParticipantes;

    @Column(name = "nivel_medio_equipe", precision = 10, scale = 2)
    private BigDecimal nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private BigDecimal totalRecompensa;

    @Column(name = "total_mvps")
    private Long totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Long participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private OffsetDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private BigDecimal indiceProntidao;
}
