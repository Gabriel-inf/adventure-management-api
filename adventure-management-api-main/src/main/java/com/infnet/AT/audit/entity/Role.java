package com.infnet.AT.audit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "audit",
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_roles_nome_por_org", columnNames = {"organizacao_id", "nome"})
                //"Duas organizações diferentes podem ter um papel chamado 'MANAGER'.
                // Mas a mesma organização não pode ter dois papéis chamados 'MANAGER'."
        }
)
@Getter@Setter
public class Role {
    @Id
    //GenerationType.IDENTITY funciona, no entanto, para escalabilidade e várias inserções
    //seria recomendado usar SEQUENCE, mantive IDENTITY, pois é mais simples e funciona.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;
    @Column(name = "descricao") //length default 255
    private String descricao;

    @CreationTimestamp //Default = now()
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    // Inicialização defensiva: Evita NullPointerException ao chamarmos um .add()
    // em um objeto instanciado em memória que ainda não foi para o banco.
    private List<Permission> permissions = new ArrayList<>();
}
