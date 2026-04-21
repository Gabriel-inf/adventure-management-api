package com.infnet.AT.operacoes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter@Setter
@EqualsAndHashCode
// Exigência do Hibernate/JPA: Sem o EqualsAndHashCode, a JVM compara os objetos pelo endereço de
// memória, o que faz o Hibernate falhar ao gerenciar duplicidades de Chaves Compostas no Cache (Session).
public class ParticipacaoMissaoId implements Serializable {
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "aventureiro_id")
    private Long aventureiroId;
}
