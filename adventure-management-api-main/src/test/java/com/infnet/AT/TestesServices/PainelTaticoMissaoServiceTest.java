package com.infnet.AT.TestesServices;

import com.infnet.AT.operacoes.dto.PainelTaticoMissaoDTO;
import com.infnet.AT.operacoes.service.PainelTaticoMissaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PainelTaticoMissaoServiceTest {

    @Autowired
    private PainelTaticoMissaoService painelTaticoMissaoService;

    @Test
    @DisplayName("Questão 1: Deve retornar o ranking das missões mais relevantes dos últimos 15 dias")
    void deveRetornarRankingTaticoRecent() {
        // Executa a busca (Lembrando que a view tem 4s de delay artificial)
        List<PainelTaticoMissaoDTO> ranking = painelTaticoMissaoService.buscarTopRankingTatico();

        assertNotNull(ranking, "A lista de ranking não deve ser nula");

        // A regra diz que deve retornar os Top 10
        assertTrue(ranking.size() <= 10, "O ranking deve conter no máximo 10 registros");

        System.out.println("--- RESULTADO DO RANKING TÁTICO ---");
        ranking.forEach(m -> {
            System.out.println(String.format("Título: %s | Atualização: %s | Prontidão: %s",
                    m.titulo(), m.ultimaAtualizacao(), m.indiceProntidao()));
        });
    }

    @Test
    void testarCache() {
        long inicio1 = System.currentTimeMillis();
        painelTaticoMissaoService.buscarTopRankingTatico();
        long fim1 = System.currentTimeMillis();
        System.out.println("Tempo 1ª chamada (sem cache): " + (fim1 - inicio1) + "ms");

        long inicio2 = System.currentTimeMillis();
        painelTaticoMissaoService.buscarTopRankingTatico();
        long fim2 = System.currentTimeMillis();
        System.out.println("Tempo 2ª chamada (com cache): " + (fim2 - inicio2) + "ms");

        assertTrue((fim2 - inicio2) < (fim1 - inicio1), "A segunda chamada deve ser muito mais rápida!");
    }
}
