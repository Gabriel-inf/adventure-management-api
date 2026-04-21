package com.infnet.AT.audit.entity;

import com.infnet.AT.audit.entity.Enum.AuditEntryAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;

@Entity
@Table(schema = "audit", name = "audit_entries")
@Getter@Setter
public class AuditEntry {
    @Id
    //GenerationType.IDENTITY funciona, no entanto, para escalabilidade e várias inserções
    //seria recomendado usar SEQUENCE, mantive IDENTITY, pois é mais simples e funciona.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne
    @JoinColumn(name = "actor_user_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "actor_api_key_id")
    private ApiKey apiKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 30)
    private AuditEntryAction action;
    @Column(name = "entity_schema", nullable = false, length = 60)
    private String entitySchema;
    @Column(name = "entity_name", nullable = false, length = 80)
    private String entityName;
    @Column(name = "entity_id", length = 80)
    private String entityId;

    @CreationTimestamp
    @Column(name = "occurred_at", nullable = false, updatable = false) // Default = now()
    private OffsetDateTime ocurredAt;

    @Column(name = "ip", columnDefinition = "inet")
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "diff", columnDefinition = "jsonb")
    private Map<String, Object> diff;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @Column(name = "success", nullable = false) //Default = true
    private Boolean success = true;
}
