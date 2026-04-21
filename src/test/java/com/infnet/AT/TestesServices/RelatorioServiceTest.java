package com.infnet.AT.TestesServices;

import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.service.RelatorioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class RelatorioServiceTest {

    @Autowired
    private RelatorioService relatorioService;

    @Test
    @DisplayName("Deve validar a sintaxe e a montagem do Ranking de Aventureiros pela Super Query")
    void deveValidarGeracaoDeRanking() {
        OffsetDateTime trintaDiasAtras = OffsetDateTime.now().minusDays(30);
        OffsetDateTime agora = OffsetDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);

        var resultado = relatorioService.obterRankingAventureiros(trintaDiasAtras, agora, null, pageable);

        assertNotNull(resultado, "A página devolutória não deve ser nula");
        assertTrue(resultado.getTotalElements() >= 0, "Deveria conseguir iterar pelos registros contados");
    }

    @Test
    @DisplayName("Deve validar sintaxe do Relatorio de Missoes cruzadas com Participantes")
    void deveValidarGeracaoDasMetricasDeMissao() {

        StatusMissao statusAlvo = StatusMissao.CONCLUIDA;

        var resultado = relatorioService.obterMetricasMissoesConcluidas(statusAlvo);

        org.junit.jupiter.api.Assertions.assertNotNull(resultado, "A lista de métricas nao deve ser nula, mesmo que o banco não tenha dados reais.");
    }

}
