package br.ifg.urt.gamercatalog_api.service;

import java.time.LocalDate;
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

        logger.info("Buscando avaliação no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Avaliação ID " + id + " não encontrada.");

                    return new RuntimeException(
                            "Avaliação não encontrada"
                    );
                });
    }

    /**
     * Busca todas as avaliações
     */
    public List<Avaliacao> findAll() {

        logger.info("Buscando todas as avaliações no banco.");

        return repository.findAll();
    }

    /**
     * REGRA DE NEGÓCIO: Busca apenas as avaliações vinculadas a um jogo específico
     */
    public List<Avaliacao> findByJogo(Long jogoId) {

        logger.info("Buscando avaliações no banco para o jogo ID: " + jogoId);

        return repository.findByJogoId(jogoId);
    }

    /**
     * REGRA DE NEGÓCIO (RF07): Cria uma nova avaliação injetando data automática e validando a nota
     */
    public Avaliacao create(Avaliacao avaliacao) {

        logger.info("Salvando nova avaliação no banco com validações de negócio.");

        if (avaliacao.getUsuario() == null || avaliacao.getJogo() == null) {
            logger.warning("Tentativa de avaliar sem usuário ou jogo vinculados.");
            throw new IllegalArgumentException(
                    "A avaliação deve possuir um usuário e um jogo válidos."
            );
        }

        // Força a validação da nota máxima/mínima definida no Model (0 a 10)
        avaliacao.setNota(avaliacao.getNota());

        // Injeta a data atual de forma automática
        avaliacao.setDataPostagem(LocalDate.now());

        return repository.save(avaliacao);
    }

    /**
     * Atualiza uma avaliação existente
     */
    @Transactional
    public Avaliacao update(Avaliacao avaliacao) {

        logger.info("Atualizando avaliação ID: "
                + avaliacao.getIdAvaliacao());

        // Verifica existência
        Avaliacao existing = repository
                .findById(avaliacao.getIdAvaliacao())
                .orElseThrow(() -> {

                    logger.warning("Avaliação ID "
                            + avaliacao.getIdAvaliacao()
                            + " não encontrada.");

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

        return repository.save(existing);
    }

    /**
     * Remove uma avaliação
     */
    public void delete(Long id) {

        logger.info("Removendo avaliação ID: " + id);

        Avaliacao existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Avaliação ID " + id + " não encontrada.");

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
    public Avaliacao alterarNota(Long id, Integer novaNota) {

        Avaliacao avaliacao = repository
                .findById(id)
                .orElseThrow(() -> {

                    logger.warning("Avaliação ID " + id + " não encontrada.");

                    return new RuntimeException(
                            "Avaliação não encontrada"
                    );
                });

        logger.info("Alterando nota da avaliação ID: " + avaliacao.getIdAvaliacao());

        // Regra de negócio no Model
        avaliacao.alterarNota(novaNota);

        return repository.save(avaliacao);
    }
}