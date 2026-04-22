package com.infnet.AT.operacoes.dto;

import java.math.BigDecimal;

public record RankingAventureiroDTO(
        Long idAventureiro,
        String nomeAventureiro,
        Long totalParticipacoes,
        BigDecimal totalRecompensas,
        Long totalDestaques
) {}

