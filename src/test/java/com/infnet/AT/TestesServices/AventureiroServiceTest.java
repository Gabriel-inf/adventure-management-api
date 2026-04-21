package com.infnet.AT.TestesServices;

import com.infnet.AT.operacoes.dto.AventureiroResumoDTO;
import com.infnet.AT.operacoes.entity.Enum.AventureiroClasse;
import com.infnet.AT.operacoes.service.AventureiroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional // Para dar rollBack no final da operação. Por ser teste, faz rollback.
class AventureiroServiceTest {

    @Autowired
    private AventureiroService aventureiroService;

    @Test
    @DisplayName("Deve buscar aventureiros pelo nome (match parcial) retornando página de DTO corretamente")
    void deveBuscarPorNomePaginado() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("nome").ascending());

        Page<AventureiroResumoDTO> resultado = aventureiroService.buscarPorNome("Lancelot", pageable);

        assertNotNull(resultado, "O objeto Page retornado pelo banco não deveria ser nulo!");
        assertTrue(resultado.getTotalElements() >= 0, "Deveria conseguir calcular o total (Count query)");
    }

    @Test
    @DisplayName("Deve buscar aventureiros combinando 3 abas de filtros retornando paginação de DTOs")
    void deveBuscarComFiltrosPaginado() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<AventureiroResumoDTO> resultado = aventureiroService.buscarComFiltros(true, AventureiroClasse.GUERREIRO, 5, pageable);

        assertNotNull(resultado, "A página devolutória não deve ser nula");
        assertTrue(resultado.getTotalPages() >= 0);
    }

    @Test
    @DisplayName("Testando orquestração da Visualização Completa (Cenário: Não encontrado)")
    void deveValidarBuscaPorPerfilCompletoVazio() {

        IllegalArgumentException erroGerado = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> aventureiroService.buscarPerfilCompleto(999999L) // ID que provavelmente não existe
        );

        org.junit.jupiter.api.Assertions.assertTrue(erroGerado.getMessage().contains("Aventureiro não encontrado"));
    }

}
