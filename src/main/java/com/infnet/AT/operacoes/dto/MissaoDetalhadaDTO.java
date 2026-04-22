package com.infnet.AT.operacoes.dto;

import com.infnet.AT.operacoes.entity.Enum.NivelPerigo;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;

import java.time.OffsetDateTime;
import java.util.List;

public record MissaoDetalhadaDTO(
        Long id,
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        OffsetDateTime dataCriacao,
        OffsetDateTime dataInicio,
        OffsetDateTime dataFim,
        List<ParticipanteMissaoDTO> participantes
) {}
