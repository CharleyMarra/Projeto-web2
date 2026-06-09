package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Dlc;
import br.ifg.urt.gamercatalog_api.repository.DlcRepository;

@Service
public class DlcService {

    private static final Logger logger =
            Logger.getLogger(DlcService.class.getName());

    // Repository para acesso ao banco
    private final DlcRepository repository;

    // Injeção via construtor
    public DlcService(DlcRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca uma DLC por ID
     */
    public Dlc findById(Long id) {

        logger.info("Buscando DLC no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("DLC ID " + id + " não encontrada.");

                    return new RuntimeException(
                            "DLC não encontrada"
                    );
                });
    }

    /**
     * Busca todas as DLCs
     */
    public List<Dlc> findAll() {

        logger.info("Buscando todas as DLCs no banco.");

        return repository.findAll();
    }

    /**
     * REGRA DE NEGÓCIO: Busca apenas as DLCs vinculadas a um jogo específico
     */
    public List<Dlc> findByJogo(Long jogoId) {

        logger.info("Buscando DLCs no banco para o jogo ID: " + jogoId);

        return repository.findByJogoId(jogoId);
    }

    /**
     * Cria uma nova DLC
     */
    public Dlc create(Dlc dlc) {

        logger.info("Salvando nova DLC no banco: " + dlc.getNome());

        return repository.save(dlc);
    }

    /**
     * Atualiza uma DLC existente
     */
    @Transactional
    public Dlc update(Dlc dlc) {

        logger.info("Atualizando DLC ID: " + dlc.getId());

        Dlc existing = repository.findById(dlc.getId())
                .orElseThrow(() -> {

                    logger.warning("DLC ID " + dlc.getId() + " não encontrada.");

                    return new RuntimeException(
                            "DLC não encontrada"
                    );
                });

        existing.setNome(dlc.getNome());
        existing.setPreco(dlc.getPreco());

        return repository.save(existing);
    }

    /**
     * Remove uma DLC
     */
    public void delete(Long id) {

        logger.info("Removendo DLC ID: " + id);

        Dlc existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("DLC ID " + id + " não encontrada.");

                    return new RuntimeException(
                            "DLC não encontrada"
                    );
                });

        repository.delete(existing);
    }
}