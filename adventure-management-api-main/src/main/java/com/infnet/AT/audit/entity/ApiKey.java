package com.infnet.AT.audit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(schema = "audit",
        name = "api_keys",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_api_keys_nome_por_org", columnNames = {"organizacao_id", "nome"})
                // Uma MESMA organização não pode cadastrar duas chaves com nomes iguais.
        }
)
@Getter@Setter
public class ApiKey {
    @Id
    //GenerationType.IDENTITY funciona, no entanto, para escalabilidade e várias inserções
    //seria recomendado usar SEQUENCE, mantive IDENTITY, pois é mais simples e funciona.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;
    @Column(name = "key_hash", nullable = false)
    private String keyHash;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true; // Default = true

    @CreationTimestamp //Default = now()
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp // O Hibernate joga a data de 'agora' automaticamente toda vez que houver um UPDATE
    @Column(name = "last_used_at")
    private OffsetDateTime lastUsedAt;
}
