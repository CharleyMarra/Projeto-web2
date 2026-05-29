package br.ifg.urt.gamercatalog_api.service;


import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.repository.FavoritosRepository;

@Service
public class FavoritosService {

    private static final Logger logger =
            Logger.getLogger(FavoritosService.class.getName());

    // Repository para acesso ao banco
    private final FavoritosRepository repository;

    // Injeção via construtor
    public FavoritosService(FavoritosRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca um favorito por ID
     */
    public Favoritos findById(Long id) {

        logger.info("Buscando favorito no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Favorito ID " + id + " não encontrado."
                    );

                    return new RuntimeException(
                            "Favorito não encontrado"
                    );
                });
    }

    /**
     * Busca todos os favoritos
     */
    public List<Favoritos> findAll() {

        logger.info("Buscando todos os favoritos no banco.");

        return repository.findAll();
    }

    /**
     * Cria um novo favorito
     */
    public Favoritos create(Favoritos favorito) {

        logger.info("Salvando novo favorito no banco.");

        // Regra de negócio
        favorito.adicionarFavorito();

        // save() cria no banco
        return repository.save(favorito);
    }

    /**
     * Atualiza um favorito existente
     */
    @Transactional
    public Favoritos update(Favoritos favorito) {

        logger.info("Atualizando favorito ID: "
                + favorito.getIdFavorito());

        // Verifica existência
        Favoritos existing = repository.findById(
                favorito.getIdFavorito()
        ).orElseThrow(() -> {

            logger.warning(
                    "Favorito ID "
                    + favorito.getIdFavorito()
                    + " não encontrado."
            );

            return new RuntimeException(
                    "Favorito não encontrado"
            );
        });

        existing.setDataAdicionado(
                favorito.getDataAdicionado()
        );

        existing.setUsuario(
                favorito.getUsuario()
        );

        existing.setJogo(
                favorito.getJogo()
        );

        // save() atualiza no banco
        return repository.save(existing);
    }

    /**
     * Remove um favorito
     */
    public void delete(Long id) {

        logger.info("Removendo favorito ID: " + id);

        Favoritos existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Favorito ID "
                            + id
                            + " não encontrado."
                    );

                    return new RuntimeException(
                            "Favorito não encontrado"
                    );
                });

        repository.delete(existing);
    }
}
