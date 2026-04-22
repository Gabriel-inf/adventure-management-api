package com.infnet.AT.operacoes.entity;

import com.infnet.AT.audit.entity.Organizacao;
import com.infnet.AT.audit.entity.Usuario;
import com.infnet.AT.operacoes.entity.Enum.AventureiroClasse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Min;

import java.time.OffsetDateTime;

@Entity
@Table(schema = "operacoes", name = "aventureiro")
@Getter@Setter
public class Aventureiro {
    @Id
    //GenerationType.IDENTITY funciona, no entanto, para escalabilidade e várias inserções
    //seria recomendado usar SEQUENCE, mantive IDENTITY, pois é mais simples e funciona.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_cadastro_id", nullable = false)
    private Usuario usuarioQueCadastrou;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "classe", nullable = false, length = 30)
    private AventureiroClasse classe;

    @Min(1)
    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private OffsetDateTime dataAtualizacao;

    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;
}
