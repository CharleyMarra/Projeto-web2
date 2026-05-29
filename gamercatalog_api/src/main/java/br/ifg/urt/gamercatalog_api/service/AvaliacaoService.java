package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Avaliacao;
import br.ifg.urt.gamercatalog_api.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {

    private static final Logger logger =
            Logger.getLogger(AvaliacaoService.class.getName());

    // Repository para acesso ao banco
    private final AvaliacaoRepository repository;

    // Injeção via construtor
    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca uma avaliação por ID
     */
    public Avaliacao findById(Long id) {

        logger.info(
                "Buscando avaliação no banco com ID: " + id
        );

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Avaliação ID " + id
                                    + " não encontrada."
                    );

                    return new RuntimeException(
                            "Avaliação não encontrada"
                    );
                });
    }

    /**
     * Busca todas as avaliações
     */
    public List<Avaliacao> findAll() {

        logger.info(
                "Buscando todas as avaliações no banco."
        );

        return repository.findAll();
    }

    /**
     * Cria uma nova avaliação
     */
    public Avaliacao create(Avaliacao avaliacao) {

        logger.info(
                "Salvando nova avaliação no banco."
        );

        // save() cria no banco
        return repository.save(avaliacao);
    }

    /**
     * Atualiza uma avaliação existente
     */
    @Transactional
    public Avaliacao update(Avaliacao avaliacao) {

        logger.info(
                "Atualizando avaliação ID: "
                        + avaliacao.getIdAvaliacao()
        );

        // Verifica existência
        Avaliacao existing = repository
                .findById(avaliacao.getIdAvaliacao())
                .orElseThrow(() -> {

                    logger.warning(
                            "Avaliação ID "
                                    + avaliacao.getIdAvaliacao()
                                    + " não encontrada."
                    );

                    return new RuntimeException(
                            "Avaliação não encontrada"
                    );
                });

        existing.setNota(avaliacao.getNota());
        existing.setTextoCritica(
                avaliacao.getTextoCritica()
        );
        existing.setDataPostagem(
                avaliacao.getDataPostagem()
        );
        existing.setUsuario(
                avaliacao.getUsuario()
        );
        existing.setJogo(
                avaliacao.getJogo()
        );

        // save() atualiza no banco
        return repository.save(existing);
    }

    /**
     * Remove uma avaliação
     */
    public void delete(Long id) {

        logger.info(
                "Removendo avaliação ID: " + id
        );

        Avaliacao existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Avaliação ID "
                                    + id
                                    + " não encontrada."
                    );

                    return new RuntimeException(
                            "Avaliação não encontrada"
                    );
                });

        repository.delete(existing);
    }

    /**
     * Altera a nota da avaliação
     */
    @Transactional
    public Avaliacao alterarNota(
            Long id,
            Integer novaNota) {

        Avaliacao avaliacao = repository
                .findById(id)
                .orElseThrow(() -> {

                    logger.warning(
                            "Avaliação ID "
                                    + id
                                    + " não encontrada."
                    );

                    return new RuntimeException(
                            "Avaliação não encontrada"
                    );
                });

        logger.info(
                "Alterando nota da avaliação ID: "
                        + avaliacao.getIdAvaliacao()
        );

        // Regra de negócio no Model
        avaliacao.alterarNota(novaNota);

        // Salva atualização
        Avaliacao avaliacaoAtualizada =
                repository.save(avaliacao);

        logger.info(
                "Nota atualizada com sucesso."
        );

        return avaliacaoAtualizada;
    }
}