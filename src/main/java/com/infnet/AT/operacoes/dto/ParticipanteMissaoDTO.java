package com.infnet.AT.operacoes.dto;

import com.infnet.AT.operacoes.entity.Enum.PapelMissao;

import java.math.BigDecimal;

public record ParticipanteMissaoDTO(
        Long idAventureiro,
        String nomeAventureiro,
        PapelMissao papel,
        BigDecimal recompensaOuro,
        Boolean destaque
) {}

