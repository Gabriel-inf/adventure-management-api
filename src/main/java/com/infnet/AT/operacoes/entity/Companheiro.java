package com.infnet.AT.operacoes.entity;

import com.infnet.AT.operacoes.entity.Enum.EspecieCompanheiro;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "operacoes", name = "companheiro")
@Getter
@Setter
public class Companheiro {
    @Id
    @Column(name = "aventureiro_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Diz ao Hibernate: "Use o ID da Pessoa como se fosse o meu próprio ID"
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false, length = 40)
    private EspecieCompanheiro especie;

    @Min(0)
    @Max(100)
    @Column(name = "indice_lealdade", nullable = false)
    private Integer indiceLealdade;
}
