package com.infnet.AT.operacoes.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PainelTaticoMissaoDTO(
        Long missaoId,
        String titulo,
        String status,
        String nivelPerigo,
        Long totalParticipantes,
        BigDecimal nivelMedioEquipe,
        BigDecimal totalRecompensa,
        Long totalMvps,
        Long participantesComCompanheiro,
        OffsetDateTime ultimaAtualizacao,
        BigDecimal indiceProntidao
) {
}
