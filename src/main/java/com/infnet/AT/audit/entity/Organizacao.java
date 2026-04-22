package com.infnet.AT.audit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(schema = "audit", name = "organizacoes")
@Getter@Setter
public class Organizacao {
    @Id
    //GenerationType.IDENTITY funciona, no entanto, para escalabilidade e várias inserções
    //seria recomendado usar SEQUENCE, mantive IDENTITY, pois é mais simples e funciona.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome", unique = true, length = 120)
    private String nome;
    @Column(nullable = false, name = "ativo") //Default = true
    private Boolean ativo = true;

    @CreationTimestamp //Default = now()
    @Column(nullable = false, name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

}
