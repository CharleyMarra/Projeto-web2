package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifg.urt.gamercatalog_api.model.Usuario;
import br.ifg.urt.gamercatalog_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private static final Logger logger =
            Logger.getLogger(UsuarioService.class.getName());

    // Repository para acesso ao banco
    private final UsuarioRepository repository;

    // Injeção via construtor
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca um usuário por ID
     */
    public Usuario findById(Long id) {

        logger.info("Buscando usuário no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Usuário ID " + id + " não encontrado.");

                    return new RuntimeException(
                            "Usuário não encontrado"
                    );
                });
    }

    /**
     * Busca todos os usuários
     */
    public List<Usuario> findAll() {

        logger.info("Buscando todos os usuários no banco.");

        return repository.findAll();
    }

    /**
     * Cria um novo usuário
     */
    public Usuario create(Usuario usuario) {

        logger.info("Salvando novo usuário no banco: "
                + usuario.getNome());

        return repository.save(usuario);
    }

    /**
     * Atualiza um usuário existente
     */
    @Transactional
    public Usuario update(Usuario usuario) {

        logger.info("Atualizando usuário ID: "
                + usuario.getId());

        Usuario existing = repository.findById(usuario.getId())
                .orElseThrow(() -> {

                    logger.warning("Usuário ID "
                            + usuario.getId()
                            + " não encontrado.");

                    return new RuntimeException(
                            "Usuário não encontrado"
                    );
                });

        existing.setNome(usuario.getNome());
        existing.setEmail(usuario.getEmail());
        existing.setSenha(usuario.getSenha());

        return repository.save(existing);
    }

    /**
     * Remove um usuário
     */
    public void delete(Long id) {

        logger.info("Removendo usuário ID: " + id);

        Usuario existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Usuário ID "
                            + id
                            + " não encontrado.");

                    return new RuntimeException(
                            "Usuário não encontrado"
                    );
                });

        repository.delete(existing);
    }
}