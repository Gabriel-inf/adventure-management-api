package com.infnet.AT.operacoes.service;

import com.infnet.AT.operacoes.dto.AventureiroPerfilCompletoDTO;
import com.infnet.AT.operacoes.dto.AventureiroResumoDTO;
import com.infnet.AT.operacoes.entity.Aventureiro;
import com.infnet.AT.operacoes.entity.Enum.AventureiroClasse;
import com.infnet.AT.operacoes.entity.Enum.EspecieCompanheiro;
import com.infnet.AT.operacoes.repository.AventureiroRepository;
import com.infnet.AT.operacoes.repository.ParticipacaoMissaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AventureiroService {

    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public AventureiroService(AventureiroRepository aventureiroRepository, ParticipacaoMissaoRepository participacaoMissaoRepository) {
        this.aventureiroRepository = aventureiroRepository;
        this.participacaoMissaoRepository = participacaoMissaoRepository;
    }


    @Transactional(readOnly = true)
    public Page<AventureiroResumoDTO> buscarPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AventureiroResumoDTO> buscarComFiltros(Boolean ativo, AventureiroClasse classe, Integer nivel, Pageable pageable) {
        return aventureiroRepository.findByAtivoAndClasseAndNivelGreaterThanEqual(ativo, classe, nivel, pageable);
    }

    @Transactional(readOnly = true) //Otimiza leitura e desliga rastreamento do Hibernate
    public AventureiroPerfilCompletoDTO buscarPerfilCompleto(Long id) {

        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aventureiro não encontrado"));

        // Para evitar o nullPointerException
        String nomeComp = null;
        EspecieCompanheiro espComp = null;
        Integer lealdadeComp = null;

        if (aventureiro.getCompanheiro() != null) {
            nomeComp = aventureiro.getCompanheiro().getNome();
            espComp = aventureiro.getCompanheiro().getEspecie();
            lealdadeComp = aventureiro.getCompanheiro().getIndiceLealdade();
        }

        Long totalMissao = participacaoMissaoRepository.countByAventureiroId(id);
        String tituloUltimaMissao = null;

        // Traz o Optional que criei no repositório
        var ultimaPart = participacaoMissaoRepository.findTopByAventureiroIdOrderByDataRegistroDesc(id);

        // Se a "caixa" não estiver vazia provando que ele possui missão, extrai o título dela
        if (ultimaPart.isPresent()) {
            tituloUltimaMissao = ultimaPart.get().getMissao().getTitulo();
        }


        return new AventureiroPerfilCompletoDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getAtivo(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                nomeComp,
                espComp,
                lealdadeComp,
                totalMissao,
                tituloUltimaMissao
        );
    }
}
