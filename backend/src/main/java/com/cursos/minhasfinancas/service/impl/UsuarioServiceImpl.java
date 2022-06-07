package com.cursos.minhasfinancas.service.impl;

import com.cursos.minhasfinancas.exception.ErroAutenticacao;
import com.cursos.minhasfinancas.exception.RegraNegocioException;
import com.cursos.minhasfinancas.model.entity.Usuario;
import com.cursos.minhasfinancas.model.repository.UsuarioRepository;
import com.cursos.minhasfinancas.service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
        }

        if (!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacao("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = usuarioRepository.existsByEmail(email);

        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse email.");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
