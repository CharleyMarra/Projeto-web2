package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Publisher;
import br.ifg.urt.gamercatalog_api.repository.PublisherRepository;

@Service
public class PublisherService {

    private static final Logger logger =
            Logger.getLogger(PublisherService.class.getName());

    // Repository para acesso ao banco
    private final PublisherRepository repository;

    // Injeção via construtor
    public PublisherService(PublisherRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca um publisher por ID
     */
    public Publisher findById(Long id) {

        logger.info("Buscando publisher no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Publisher ID "
                            + id
                            + " não encontrado.");

                    return new RuntimeException(
                            "Publisher não encontrado"
                    );
                });
    }

    /**
     * Busca todos os publishers
     */
    public List<Publisher> findAll() {

        logger.info("Buscando todos os publishers no banco.");

        return repository.findAll();
    }

    /**
     * Cria um novo publisher
     */
    public Publisher create(Publisher publisher) {

        logger.info("Salvando novo publisher no banco: "
                + publisher.getNome());

        // save() cria no banco
        return repository.save(publisher);
    }

    /**
     * Atualiza um publisher existente
     */
    @Transactional
    public Publisher update(Publisher publisher) {

        logger.info("Atualizando publisher ID: "
                + publisher.getId());

        // Verifica existência
        Publisher existing = repository.findById(
                        publisher.getId()
                )
                .orElseThrow(() -> {

                    logger.warning("Publisher ID "
                            + publisher.getId()
                            + " não encontrado.");

                    return new RuntimeException(
                            "Publisher não encontrado"
                    );
                });

        existing.setNome(publisher.getNome());
        existing.setSede(
                publisher.getSede()
        );

        // save() atualiza no banco
        return repository.save(existing);
    }

    /**
     * Remove um publisher
     */
    public void delete(Long id) {

        logger.info("Removendo publisher ID: " + id);

        Publisher existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Publisher ID "
                            + id
                            + " não encontrado.");

                    return new RuntimeException(
                            "Publisher não encontrado"
                    );
                });

        repository.delete(existing);
    }
}
