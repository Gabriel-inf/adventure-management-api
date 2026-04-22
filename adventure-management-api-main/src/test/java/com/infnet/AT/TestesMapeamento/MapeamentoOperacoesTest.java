package com.infnet.AT.TestesMapeamento;

import com.infnet.AT.audit.entity.Enum.UsuariosStatus;
import com.infnet.AT.audit.entity.Organizacao;
import com.infnet.AT.audit.entity.Usuario;
import com.infnet.AT.audit.repository.OrganizacaoRepository;
import com.infnet.AT.audit.repository.UsuarioRepository;
import com.infnet.AT.operacoes.entity.Aventureiro;
import com.infnet.AT.operacoes.entity.Companheiro;
import com.infnet.AT.operacoes.entity.Enum.*;
import com.infnet.AT.operacoes.entity.Missao;
import com.infnet.AT.operacoes.entity.ParticipacaoMissao;
import com.infnet.AT.operacoes.repository.AventureiroRepository;
import com.infnet.AT.operacoes.repository.MissaoRepository;
import com.infnet.AT.operacoes.repository.ParticipacaoMissaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
public class MapeamentoOperacoesTest {
    @Autowired
    private OrganizacaoRepository orgRepo;
    @Autowired private UsuarioRepository usuRepo;
    @Autowired private AventureiroRepository avRepo;
    @Autowired private MissaoRepository missaoRepo;
    @Autowired private ParticipacaoMissaoRepository pmRepo;
    @Test
    void deveSalvarFluxoCompletoDeAventura() {

        Organizacao org = new Organizacao();
        org.setNome("Guilda dos Testadores");
        org = orgRepo.save(org);

        Usuario admin = new Usuario();
        admin.setNome("Admin Validador");
        admin.setEmail("validador@guilda.com");
        admin.setSenhaHash("1234");
        admin.setStatus(UsuariosStatus.ATIVO);
        admin.setOrganizacao(org);
        admin = usuRepo.save(admin);

        Aventureiro heroi = new Aventureiro();
        heroi.setNome("Gabriel O Grande");
        heroi.setClasse(AventureiroClasse.GUERREIRO);
        heroi.setNivel(5);
        heroi.setAtivo(true);
        heroi.setOrganizacao(org);
        heroi.setUsuarioQueCadastrou(admin);

        Companheiro pet = new Companheiro();
        pet.setNome("Bolinha");
        pet.setEspecie(EspecieCompanheiro.LOBO);
        pet.setIndiceLealdade(95);
        pet.setAventureiro(heroi);

        heroi.setCompanheiro(pet);
        heroi = avRepo.save(heroi);

        Assertions.assertNotNull(heroi.getId(), "Aventureiro não foi salvo!");
        Assertions.assertNotNull(heroi.getCompanheiro().getId(), "O ID do Companheiro deve ser a cópia do Aventureiro (MapsId)!");
        Assertions.assertEquals(heroi.getId(), heroi.getCompanheiro().getId(), "Os IDs não são compartilhados!");

        Missao quest = new Missao();
        quest.setTitulo("Testar os Repositórios do Spring");
        quest.setNivelPerigo(NivelPerigo.ALTO);
        quest.setStatus(StatusMissao.PLANEJADA);
        quest.setOrganizacao(org);
        quest = missaoRepo.save(quest);

        Assertions.assertNotNull(quest.getId(), "Missão não foi salva!");

        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setMissao(quest);
        participacao.setAventureiro(heroi);
        participacao.setPapel(PapelMissao.LIDER);
        participacao.setRecompensaOuro(new BigDecimal("500.50"));
        participacao.setDestaque(true);
        participacao = pmRepo.save(participacao);

        Assertions.assertNotNull(participacao.getId(), "A matrícula conjunta falhou");
        Assertions.assertEquals(heroi.getId(), participacao.getId().getAventureiroId(), "A chave embutida do aventureiro não colou");
        Assertions.assertEquals(quest.getId(), participacao.getId().getMissaoId(), "A chave embutida da missao não colou");

        System.out.println("== TESTE CONCLUÍDO COM SUCESSO! INTEGRIDADE DE SCHEMAS MANTIDA ==");
    }
}
