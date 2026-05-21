package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.repository.JogoRepository;

@Service
public class JogoService {

    private static final Logger logger =
            Logger.getLogger(JogoService.class.getName());

    // Repository para acesso ao banco
    private final JogoRepository repository;

    // Injeção via construtor
    public JogoService(JogoRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca um jogo por ID
     */
    public Jogo findById(Long id) {

        logger.info("Buscando jogo no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Jogo ID " + id + " não encontrado.");

                    return new RuntimeException(
                            "Jogo não encontrado"
                    );
                });
    }

    /**
     * Busca todos os jogos
     */
    public List<Jogo> findAll() {

        logger.info("Buscando todos os jogos no banco.");

        return repository.findAll();
    }

    /**
     * Cria um novo jogo
     */
    public Jogo create(Jogo jogo) {

        logger.info("Salvando novo jogo no banco: "
                + jogo.getTitulo());

        // save() cria no banco
        return repository.save(jogo);
    }

    /**
     * Atualiza um jogo existente
     */
    @Transactional
    public Jogo update(Jogo jogo) {

        logger.info("Atualizando jogo ID: "
                + jogo.getId());

        // Verifica existência
        Jogo existing = repository.findById(jogo.getId())
                .orElseThrow(() -> {

                    logger.warning("Jogo ID "
                            + jogo.getId()
                            + " não encontrado.");

                    return new RuntimeException(
                            "Jogo não encontrado"
                    );
                });

        existing.setTitulo(jogo.getTitulo());
        existing.setDescricao(jogo.getDescricao());
        existing.setPreco(jogo.getPreco());
        existing.setGenero(jogo.getGenero());
        existing.setClassificacaoIndicativa(
                jogo.getClassificacaoIndicativa()
        );

        // save() atualiza no banco
        return repository.save(existing);
    }

    /**
     * Remove um jogo
     */
    public void delete(Long id) {

        logger.info("Removendo jogo ID: " + id);

        Jogo existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Jogo ID "
                            + id
                            + " não encontrado.");

                    return new RuntimeException(
                            "Jogo não encontrado"
                    );
                });

        repository.delete(existing);
    }

    /**
     * Altera o preço do jogo
     */
    @Transactional
    public Jogo alterarPreco(Long id, Double novoPreco) {

        Jogo jogo = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Jogo ID "
                            + id
                            + " não encontrado.");

                    return new RuntimeException(
                            "Jogo não encontrado"
                    );
                });

        logger.info("Alterando preço do jogo: "
                + jogo.getTitulo());

        // Regra de negócio no Model
        jogo.alterarPreco(novoPreco);

        // Salva atualização
        Jogo jogoAtualizado = repository.save(jogo);

        logger.info("Preço atualizado com sucesso.");

        return jogoAtualizado;
    }
}