package com.infnet.AT.operacoes.entity;

import com.infnet.AT.audit.entity.Organizacao;
import com.infnet.AT.operacoes.entity.Enum.NivelPerigo;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(schema = "operacoes", name = "missao")
@Getter@Setter
public class Missao {
    @Id
    //GenerationType.IDENTITY funciona, no entanto, para escalabilidade e várias inserções
    //seria recomendado usar SEQUENCE, mantive IDENTITY, pois é mais simples e funciona.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", nullable = false, length = 20)
    private NivelPerigo nivelPerigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusMissao status;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;
}
