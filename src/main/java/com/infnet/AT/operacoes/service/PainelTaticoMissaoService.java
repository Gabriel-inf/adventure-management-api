package com.infnet.AT.operacoes.service;

import com.infnet.AT.operacoes.entity.PainelTaticoMissao;
import com.infnet.AT.operacoes.repository.PainelTaticoMissaoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PainelTaticoMissaoService {

    private final PainelTaticoMissaoRepository repository;

    public PainelTaticoMissaoService(PainelTaticoMissaoRepository repository) {
        this.repository = repository;
    }

    @Cacheable("rankingTatico")
    @Transactional(readOnly = true)
    public List<PainelTaticoMissao> buscarTopRankingTatico() {
        // Últimos 15 dias
        OffsetDateTime limite = OffsetDateTime.now().minusDays(15);

        // Top 10 resultados (página 0, tamanho 10)
        PageRequest top10 = PageRequest.of(0, 10);

        return repository.findByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(limite, top10);
    }
}
