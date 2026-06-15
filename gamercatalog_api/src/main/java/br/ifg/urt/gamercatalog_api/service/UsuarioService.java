package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Importante: Esses dois caras aqui embaixo controlam a paginação
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ifg.urt.gamercatalog_api.model.Usuario;
import br.ifg.urt.gamercatalog_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario findById(Long id) {
        logger.info("Buscando usuário no banco com ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // OLHA O PAGEABLE AQUI: mudamos de List para Page no retorno e injetamos o Pageable no parâmetro
    public Page<Usuario> findAll(String nome, Pageable pageable) {
        logger.info("Buscando usuários paginados. Filtro: " + nome);

        // Se tem nome no filtro, busca paginado por nome. Se não, traz tudo paginado.
        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, pageable);
        }

        return repository.findAll(pageable);
    }

    public Usuario create(Usuario usuario) {
        logger.info("Salvando novo usuário: " + usuario.getNome());
        return repository.save(usuario);
    }

    @Transactional
    public Usuario update(Usuario usuario) {
        logger.info("Atualizando usuário ID: " + usuario.getId());
        Usuario existing = repository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existing.setNome(usuario.getNome());
        existing.setEmail(usuario.getEmail());
        existing.setSenha(usuario.getSenha());

        return repository.save(existing);
    }

    public void delete(Long id) {
        logger.info("Removendo usuário ID: " + id);
        Usuario existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repository.delete(existing);
    }
}