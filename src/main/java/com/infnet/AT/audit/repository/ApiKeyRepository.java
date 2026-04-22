package com.infnet.AT.audit.repository;

import com.infnet.AT.audit.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    // Método criado para ignorar o carregamento lento da Tabela inteira e executa a atualização certeira.
    @Modifying // Avisa ao Spring que a instrução abaixo não é de "leitura" (Select)
    @Transactional // Cria uma barreira de proteção de transação do banco (obrigatório em UPDATE Customizado)
    @Query("UPDATE ApiKey a SET a.lastUsedAt = :dataAtual WHERE a.id = :idApiKey")
    void aplicarLastUsed(@Param("idApiKey") Long idApiKey, @Param("dataAtual")OffsetDateTime dataAtual);
}
