package com.infnet.AT.audit.entity;

import com.infnet.AT.audit.entity.Enum.UsuariosStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "audit",
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_usuarios_email_por_org", columnNames = {"organizacao_id", "email"})
                // Em uma organização não pode haver dois usuários com o mesmo email
        }
)
@Getter@Setter
public class Usuario {
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
    @Column(name = "email", nullable = false, length = 180)
    private String email;
    @Column(name = "senha_hash", nullable = false) //length default 255
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private UsuariosStatus status;

    @Column(name = "ultimo_login_em")
    private OffsetDateTime ultimoLoginEm;

    @CreationTimestamp //Default = now()
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp // O Hibernate joga a data de 'agora' automaticamente toda vez que houver um UPDATE
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            schema = "audit",
            name = "user_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
}
