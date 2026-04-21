package com.infnet.AT.operacoes.repository;

import com.infnet.AT.operacoes.dto.MissaoResumoDTO;
import com.infnet.AT.operacoes.dto.RelatorioMissaoMetricaDTO;
import com.infnet.AT.operacoes.entity.Enum.NivelPerigo;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.entity.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {
    Page<MissaoResumoDTO> findByStatusAndNivelPerigoAndDataCriacaoBetween(
            StatusMissao status,
            NivelPerigo nivelPerigo,
            OffsetDateTime dataInicial,
            OffsetDateTime dataFinal,
            Pageable pageable
    );

    @Query("""
        SELECT new com.infnet.AT.operacoes.dto.RelatorioMissaoMetricaDTO(
            m.nivelPerigo,
            COUNT(DISTINCT m.id),
            COUNT(p.aventureiro.id),
            SUM(p.recompensaOuro)
        )
        FROM Missao m
        LEFT JOIN ParticipacaoMissao p ON p.missao = m
        WHERE m.status = :status
        GROUP BY m.nivelPerigo
    """)
    List<RelatorioMissaoMetricaDTO> gerarRelatorioMissoesConcluidas(@Param("status") StatusMissao status);


}
