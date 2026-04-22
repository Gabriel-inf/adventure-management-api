package com.infnet.AT.operacoes.dto;

import com.infnet.AT.operacoes.entity.Enum.NivelPerigo;

import java.math.BigDecimal;

public record RelatorioMissaoMetricaDTO(
        NivelPerigo nivelPerigo,
        Long quantidadeMissoes,
        Long aventureirosEnvolvidos,
        BigDecimal valorTotalInvestido
) {}

