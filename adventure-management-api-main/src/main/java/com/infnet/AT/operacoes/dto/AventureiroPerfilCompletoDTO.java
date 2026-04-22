package com.infnet.AT.operacoes.dto;

import com.infnet.AT.operacoes.entity.Enum.AventureiroClasse;
import com.infnet.AT.operacoes.entity.Enum.EspecieCompanheiro;

public record AventureiroPerfilCompletoDTO(
        //Aventureiro
        Long id,
        String nome,
        Boolean ativo,
        AventureiroClasse classe,
        Integer nivel,
        //Companheiro
        String nomeCompanheiro,
        EspecieCompanheiro especie,
        Integer indiceLealdade,
        //Missão
        Long quantTotalPartMissao,
        String tituloUltimaMissaoReg
) {
}
