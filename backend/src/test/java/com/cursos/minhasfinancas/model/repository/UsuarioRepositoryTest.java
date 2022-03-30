package com.cursos.minhasfinancas.model.repository;

import com.cursos.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        // acao/execução
        boolean result = usuarioRepository.existsByEmail(usuario.getEmail());

        // verificacao
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        // acao
        boolean result = usuarioRepository.existsByEmail("usuario@email.com");

        // verificao
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        // cenario
        Usuario usuario = criarUsuario();

        // acao
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        // cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        // verificacao
        Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQuandoNaoExisteNaBase(){
        // verificacao
        Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isFalse();
    }

    public static Usuario criarUsuario(){
        return Usuario.builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("senha")
                .build();

    }
}
