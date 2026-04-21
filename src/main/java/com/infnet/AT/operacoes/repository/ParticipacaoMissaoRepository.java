package com.infnet.AT.operacoes.repository;

import com.infnet.AT.operacoes.dto.RankingAventureiroDTO;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.entity.ParticipacaoMissao;
import com.infnet.AT.operacoes.entity.ParticipacaoMissaoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {

    // Conta quantas participacoes existem vinculadas ao id daquele aventureiro
    Long countByAventureiroId(Long idAventureiro);

    // Encontra a "Top 1" participacao vinculada aquele aventureiro filtrada do mais recente pro mais antigo
    Optional<ParticipacaoMissao> findTopByAventureiroIdOrderByDataRegistroDesc(Long idAventureiro);

    // Busca todos os aventureiros que participam de uma missão especifica
    @Query("SELECT p FROM ParticipacaoMissao p JOIN FETCH p.aventureiro WHERE p.missao.id = :idMissao")
    List<ParticipacaoMissao> buscarParticipacoesComAventureiro(@Param("idMissao") Long idMissao);

    // Super Query para gerar o ranking de aventureiros

    @Query("""
        SELECT new com.infnet.AT.operacoes.dto.RankingAventureiroDTO(
            a.id,
            a.nome,
            COUNT(p),
            SUM(p.recompensaOuro),
            SUM(CASE WHEN p.destaque = true THEN 1L ELSE 0L END)
        )
        FROM ParticipacaoMissao p
        JOIN p.aventureiro a
        JOIN p.missao m
        WHERE p.dataRegistro BETWEEN :inicio AND :fim
          AND (:status IS NULL OR m.status = :status)
        GROUP BY a.id, a.nome
    """)
    Page<RankingAventureiroDTO> gerarRankingAventureiros(
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("status") StatusMissao status,
            Pageable pageable
    );


}
