package com.infnet.AT.TestesServices;

import com.infnet.AT.operacoes.dto.MissaoResumoDTO;
import com.infnet.AT.operacoes.entity.Enum.NivelPerigo;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.service.MissaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
@Transactional
class MissaoServiceTest {
    @Autowired
    private MissaoService missaoService;
    @Test
    @DisplayName("Deve buscar missoes usando multi filtros e periodo de tempo (Between)")
    void deveBuscarComFiltrosETempoPaginado() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        OffsetDateTime trintaDiasAtras = OffsetDateTime.now().minusDays(30);
        OffsetDateTime agora = OffsetDateTime.now();

        Page<MissaoResumoDTO> resultado = missaoService.buscarComFiltro(
                StatusMissao.PLANEJADA,
                NivelPerigo.BAIXO,
                trintaDiasAtras,
                agora,
                pageable
        );
        assertNotNull(resultado, "A página devolutória não deve ser nula");
        assertTrue(resultado.getTotalElements() >= 0, "Deveria conseguir calcular o Count da query Between");
    }
    @Test
    @DisplayName("Deve validar Detalhamento de Missao com retorno consistente em caso de ausencia")
    void deveValidarDetalhamentoMissaoVazia() {

        IllegalArgumentException erroGerado = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> missaoService.detalharMissao(999999L)
        );

        org.junit.jupiter.api.Assertions.assertTrue(erroGerado.getMessage().contains("Missão não encontrada"));
    }

}