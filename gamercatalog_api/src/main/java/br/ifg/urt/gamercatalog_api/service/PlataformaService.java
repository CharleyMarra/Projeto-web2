package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Plataforma;
import br.ifg.urt.gamercatalog_api.repository.PlataformaRepository;

@Service
public class PlataformaService {

    private static final Logger logger =
            Logger.getLogger(PlataformaService.class.getName());

    // Repository para acesso ao banco
    private final PlataformaRepository repository;

    // Injeção via construtor
    public PlataformaService(PlataformaRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca uma plataforma por ID
     */
    public Plataforma findById(Long id) {

        logger.info("Buscando plataforma no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Plataforma ID "
                            + id
                            + " não encontrada.");

                    return new RuntimeException(
                            "Plataforma não encontrada"
                    );
                });
    }

    /**
     * Busca todas as plataformas
     */
    public List<Plataforma> findAll() {

        logger.info("Buscando todas as plataformas no banco.");

        return repository.findAll();
    }

    /**
     * Cria uma nova plataforma
     */
    public Plataforma create(Plataforma plataforma) {

        logger.info("Salvando nova plataforma no banco: "
                + plataforma.getNome());

        // save() cria no banco
        return repository.save(plataforma);
    }

    /**
     * Atualiza uma plataforma existente
     */
    @Transactional
    public Plataforma update(Plataforma plataforma) {

        logger.info("Atualizando plataforma ID: "
                + plataforma.getId());

        // Verifica existência
        Plataforma existing = repository.findById(
                        plataforma.getId()
                )
                .orElseThrow(() -> {

                    logger.warning("Plataforma ID "
                            + plataforma.getId()
                            + " não encontrada.");

                    return new RuntimeException(
                            "Plataforma não encontrada"
                    );
                });

        existing.setNome(plataforma.getNome());
        existing.setFabricante(
                plataforma.getFabricante()
        );

        // save() atualiza no banco
        return repository.save(existing);
    }

    /**
     * Remove uma plataforma
     */
    public void delete(Long id) {

        logger.info("Removendo plataforma ID: " + id);

        Plataforma existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Plataforma ID "
                            + id
                            + " não encontrada.");

                    return new RuntimeException(
                            "Plataforma não encontrada"
                    );
                });

        repository.delete(existing);
    }
}
