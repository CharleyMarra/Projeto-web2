package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Comentarios;
import br.ifg.urt.gamercatalog_api.repository.ComentariosRepository;

@Service
public class ComentariosService {

    private static final Logger logger =
            Logger.getLogger(ComentariosService.class.getName());

    // Repository para acesso ao banco
    private final ComentariosRepository repository;

    // Injeção via construtor
    public ComentariosService(
            ComentariosRepository repository) {

        this.repository = repository;
    }

    /**
     * Busca um comentário por ID
     */
    public Comentarios findById(Long id) {

        logger.info(
                "Buscando comentário no banco com ID: " + id
        );

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Comentário ID "
                                    + id
                                    + " não encontrado."
                    );

                    return new RuntimeException(
                            "Comentário não encontrado"
                    );
                });
    }

    /**
     * Busca todos os comentários
     */
    public List<Comentarios> findAll() {

        logger.info(
                "Buscando todos os comentários no banco."
        );

        return repository.findAll();
    }

    /**
     * Cria um novo comentário
     */
    public Comentarios create(
            Comentarios comentario) {

        logger.info(
                "Salvando novo comentário no banco."
        );

        // save() cria no banco
        return repository.save(comentario);
    }

    /**
     * Atualiza um comentário existente
     */
    @Transactional
    public Comentarios update(
            Comentarios comentario) {

        logger.info(
                "Atualizando comentário ID: "
                        + comentario.getIdComentario()
        );

        // Verifica existência
        Comentarios existing =
                repository.findById(
                        comentario.getIdComentario()
                )
                .orElseThrow(() -> {

                    logger.warning(
                            "Comentário ID "
                                    + comentario.getIdComentario()
                                    + " não encontrado."
                    );

                    return new RuntimeException(
                            "Comentário não encontrado"
                    );
                });

        existing.setTexto(comentario.getTexto());
        existing.setDataHora(
                comentario.getDataHora()
        );
        existing.setUsuario(
                comentario.getUsuario()
        );
        existing.setJogo(
                comentario.getJogo()
        );

        // save() atualiza no banco
        return repository.save(existing);
    }

    /**
     * Remove um comentário
     */
    public void delete(Long id) {

        logger.info(
                "Removendo comentário ID: " + id
        );

        Comentarios existing =
                repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Comentário ID "
                                    + id
                                    + " não encontrado."
                    );

                    return new RuntimeException(
                            "Comentário não encontrado"
                    );
                });

        repository.delete(existing);
    }

    /**
     * Edita o texto do comentário
     */
    @Transactional
    public Comentarios editarComentario(
            Long id,
            String novoTexto) {

        Comentarios comentario =
                repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Comentário ID "
                                    + id
                                    + " não encontrado."
                    );

                    return new RuntimeException(
                            "Comentário não encontrado"
                    );
                });

        logger.info(
                "Editando comentário ID: " + id
        );

        // Regra de negócio no Model
        comentario.editarComentario(novoTexto);

        // Salva atualização
        Comentarios comentarioAtualizado =
                repository.save(comentario);

        logger.info(
                "Comentário atualizado com sucesso."
        );

        return comentarioAtualizado;
    }
}
