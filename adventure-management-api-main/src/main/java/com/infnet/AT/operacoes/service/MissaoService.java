package com.infnet.AT.operacoes.service;

import com.infnet.AT.operacoes.dto.MissaoDetalhadaDTO;
import com.infnet.AT.operacoes.dto.MissaoResumoDTO;
import com.infnet.AT.operacoes.dto.ParticipanteMissaoDTO;
import com.infnet.AT.operacoes.entity.Enum.NivelPerigo;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.entity.Missao;
import com.infnet.AT.operacoes.entity.ParticipacaoMissao;
import com.infnet.AT.operacoes.repository.MissaoRepository;
import com.infnet.AT.operacoes.repository.ParticipacaoMissaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MissaoService {
    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public MissaoService(MissaoRepository missaoRepository, ParticipacaoMissaoRepository participacaoMissaoRepository) {
        this.missaoRepository = missaoRepository;
        this.participacaoMissaoRepository = participacaoMissaoRepository;
    }

    @Transactional(readOnly = true)
    public Page<MissaoResumoDTO> buscarComFiltro(StatusMissao status,
                                                 NivelPerigo nivelPerigo,
                                                 OffsetDateTime dataInicial,
                                                 OffsetDateTime dataFinal,
                                                 Pageable pageable){
        return missaoRepository.findByStatusAndNivelPerigoAndDataCriacaoBetween(
                status,
                nivelPerigo,
                dataInicial,
                dataFinal,
                pageable
        );
    }

    @Transactional(readOnly = true)
    public MissaoDetalhadaDTO detalharMissao(Long idMissao) {

        Missao missao = missaoRepository.findById(idMissao)
                .orElseThrow(() -> new IllegalArgumentException("Missão não encontrada"));

        List<ParticipacaoMissao> participacoes = participacaoMissaoRepository.buscarParticipacoesComAventureiro(idMissao);

        List<ParticipanteMissaoDTO> participantesDto = participacoes.stream()
                .map(p -> new ParticipanteMissaoDTO(
                        p.getAventureiro().getId(),
                        p.getAventureiro().getNome(),
                        p.getPapel(),
                        p.getRecompensaOuro(),
                        p.getDestaque()
                )).toList();

        return new MissaoDetalhadaDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelPerigo(),
                missao.getDataCriacao(),
                missao.getDataInicio(),
                missao.getDataFim(),
                participantesDto
        );
    }

}
