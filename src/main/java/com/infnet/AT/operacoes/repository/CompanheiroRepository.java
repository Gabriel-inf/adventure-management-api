package com.infnet.AT.operacoes.repository;

import com.infnet.AT.operacoes.entity.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
}
