package com.infnet.AT.TestesMapeamento;

import com.infnet.AT.audit.entity.Enum.UsuariosStatus;
import com.infnet.AT.audit.entity.Organizacao;
import com.infnet.AT.audit.entity.Permission;
import com.infnet.AT.audit.entity.Role;
import com.infnet.AT.audit.entity.Usuario;
import com.infnet.AT.audit.repository.OrganizacaoRepository;
import com.infnet.AT.audit.repository.PermissionRepository;
import com.infnet.AT.audit.repository.RoleRepository;
import com.infnet.AT.audit.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MapeamentoAuditTest {

    @Autowired private OrganizacaoRepository organizacaoRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PermissionRepository permissionRepo;

    @Test
    void deveSalvarEMapearTodaEstruturaSemQuebrarDatabase(){

        Organizacao organizacaoTeste = new Organizacao();
        organizacaoTeste.setNome("Organização Teste");
        // Não preciso criar mais nada, pois, na teoria todos os outros campos
        // são criados sozinhos.
        organizacaoTeste = organizacaoRepo.save(organizacaoTeste);

        Permission permissionTeste = new Permission();
        permissionTeste.setCode("ORGANIZACAO_READ");
        permissionTeste.setDescricao("Ler Organização");
        permissionTeste = permissionRepo.save(permissionTeste);

        Role roleTeste = new Role();
        roleTeste.setNome("ADMIN");
        roleTeste.setOrganizacao(organizacaoTeste);
        roleTeste.getPermissions().add(permissionTeste);
        roleTeste = roleRepo.save(roleTeste);

        Usuario usuarioTeste = new Usuario();
        usuarioTeste.setNome("Usuário Teste");
        usuarioTeste.setEmail("email@teste.com");
        usuarioTeste.setSenhaHash("senha_hash_fake_teste");
        usuarioTeste.setStatus(UsuariosStatus.ATIVO);
        usuarioTeste.setOrganizacao(organizacaoTeste);
        usuarioTeste.getRoles().add(roleTeste);

        Usuario usuarioSalvo = usuarioRepo.save(usuarioTeste);

        Assertions.assertNotNull(usuarioSalvo.getId(),
                "O banco teria que retornar o ID do usuarioTeste se foi salvo!");
        String organizacaoAQuePertence = usuarioSalvo.getOrganizacao().getNome();
        Assertions.assertEquals("Organização Teste", organizacaoAQuePertence);

        int qntPermissions = usuarioSalvo.getRoles().getFirst().getPermissions().size();
        System.out.println("=== O usuário possui " + qntPermissions + " permissões! ===");

        Assertions.assertTrue(qntPermissions >= 1,
                "Deveria ter achado a ponte de ligações e retornado ao menos 1 permissão!");
    }
}
