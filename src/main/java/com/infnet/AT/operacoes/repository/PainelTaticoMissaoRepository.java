package com.infnet.AT.operacoes.repository;

import com.infnet.AT.operacoes.entity.PainelTaticoMissao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {

    // Busca registros após uma data, ordena por índice desc e permite limitar via Pageable
    List<PainelTaticoMissao> findByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(
            OffsetDateTime dataLimite,
            Pageable pageable
    );
}
