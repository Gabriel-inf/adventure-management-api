package com.infnet.AT.operacoes.repository;

import com.infnet.AT.operacoes.dto.AventureiroResumoDTO;
import com.infnet.AT.operacoes.entity.Aventureiro;
import com.infnet.AT.operacoes.entity.Enum.AventureiroClasse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {
    // Utilizando DTO para uma busca mais assertiva, com apenas os campos necessários
    // Pageable permite paginação e ordenação, Sort.by("nome") ou Sort.by("nivel")
    Page<AventureiroResumoDTO> findByNomeContainingIgnoreCase (String nome, Pageable pageable);

    Page<AventureiroResumoDTO> findByAtivoAndClasseAndNivelGreaterThanEqual(
            Boolean ativo,
            AventureiroClasse classe,
            Integer nivel,
            Pageable pageable
    );
}
