package com.infnet.AT.operacoes.dto;

import com.infnet.AT.operacoes.entity.Enum.AventureiroClasse;

public record AventureiroResumoDTO(
        Long id,
        String nome,
        AventureiroClasse classe,
        Integer nivel,
        Boolean ativo
) {
}
