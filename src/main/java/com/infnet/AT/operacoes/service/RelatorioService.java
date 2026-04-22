package com.infnet.AT.operacoes.service;

import com.infnet.AT.operacoes.dto.RankingAventureiroDTO;
import com.infnet.AT.operacoes.dto.RelatorioMissaoMetricaDTO;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.repository.MissaoRepository;
import com.infnet.AT.operacoes.repository.ParticipacaoMissaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class RelatorioService {

    private final ParticipacaoMissaoRepository participacaoMissaoRepository;
    private final MissaoRepository missaoRepository;

    public RelatorioService(ParticipacaoMissaoRepository participacaoMissaoRepository, MissaoRepository missaoRepository) {
        this.participacaoMissaoRepository = participacaoMissaoRepository;
        this.missaoRepository = missaoRepository;
    }

    @Transactional(readOnly = true)
    public Page<RankingAventureiroDTO> obterRankingAventureiros(
            OffsetDateTime dataInicio,
            OffsetDateTime dataFim,
            StatusMissao statusOpcional,
            Pageable pageable) {

        return participacaoMissaoRepository.gerarRankingAventureiros(dataInicio, dataFim, statusOpcional, pageable);
    }

    @Transactional(readOnly = true)
    public List<RelatorioMissaoMetricaDTO> obterMetricasMissoesConcluidas(StatusMissao status) {
        return missaoRepository.gerarRelatorioMissoesConcluidas(status);
    }

}
