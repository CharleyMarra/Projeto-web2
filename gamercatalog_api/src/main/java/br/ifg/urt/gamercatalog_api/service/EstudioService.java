package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Estudio;
import br.ifg.urt.gamercatalog_api.repository.EstudioRepository;

@Service
public class EstudioService {

    private static final Logger logger =
            Logger.getLogger(EstudioService.class.getName());

    // Repository para acesso ao banco
    private final EstudioRepository repository;

    // Injeção via construtor
    public EstudioService(EstudioRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca um estúdio por ID
     */
    public Estudio findById(Long id) {

        logger.info("Buscando estúdio no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Estúdio ID "
                            + id
                            + " não encontrado.");

                    return new RuntimeException(
                            "Estúdio não encontrado"
                    );
                });
    }

    /**
     * Busca todos os estúdios
     */
    public List<Estudio> findAll() {

        logger.info("Buscando todos os estúdios no banco.");

        return repository.findAll();
    }

    /**
     * Cria um novo estúdio
     */
    public Estudio create(Estudio estudio) {

        logger.info("Salvando novo estúdio no banco: "
                + estudio.getNome());

        // save() cria no banco
        return repository.save(estudio);
    }

    /**
     * Atualiza um estúdio existente
     */
    @Transactional
    public Estudio update(Estudio estudio) {

        logger.info("Atualizando estúdio ID: "
                + estudio.getId());

        // Verifica existência
        Estudio existing = repository.findById(
                        estudio.getId()
                )
                .orElseThrow(() -> {

                    logger.warning("Estúdio ID "
                            + estudio.getId()
                            + " não encontrado.");

                    return new RuntimeException(
                            "Estúdio não encontrado"
                    );
                });

        existing.setNome(estudio.getNome());
        existing.setPais(
                estudio.getPais()
        );

        // save() atualiza no banco
        return repository.save(existing);
    }

    /**
     * Remove um estúdio
     */
    public void delete(Long id) {

        logger.info("Removendo estúdio ID: " + id);

        Estudio existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Estúdio ID "
                            + id
                            + " não encontrado.");

                    return new RuntimeException(
                            "Estúdio não encontrado"
                    );
                });

        repository.delete(existing);
    }
}
