package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifg.urt.gamercatalog_api.model.Conquista;
import br.ifg.urt.gamercatalog_api.repository.ConquistaRepository;
import org.springframework.data.domain.Page; // Adicionado
import org.springframework.data.domain.Pageable; // Adicionado

@Service
public class ConquistaService {

    private static final Logger logger =
            Logger.getLogger(ConquistaService.class.getName());

    // Repository para acesso ao banco
    private final ConquistaRepository repository;

    // Injeção via construtor
    public ConquistaService(ConquistaRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca uma conquista por ID
     */
    public Conquista findById(Long id) {

        logger.info("Buscando conquista no banco com ID: " + id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Conquista ID " + id + " não encontrada.");

                    return new RuntimeException(
                            "Conquista não encontrada"
                    );
                });
    }

    /**
     * Busca todas as conquistas paginadas e com filtro opcional por nome (título)
     */
    public Page<Conquista> findAll(String nome, Pageable pageable) {
        logger.info("Buscando todas as conquistas no banco com paginação. Filtro nome: " + nome);
        Page<Conquista> pagina;

        // Verifica se o usuário enviou algum nome para filtrar pelo título
        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByTituloContainingIgnoreCase(nome, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }

        return pagina;
    }

    /**
     * Cria uma nova conquista
     */
    public Conquista create(Conquista conquista) {

        logger.info("Salvando nova conquista no banco: "
                + conquista.getTitulo());

        return repository.save(conquista);
    }

    /**
     * Atualiza uma conquista existente
     */
    @Transactional
    public Conquista update(Conquista conquista) {

        logger.info("Atualizando conquista ID: "
                + conquista.getId());

        Conquista existing = repository.findById(conquista.getId())
                .orElseThrow(() -> {

                    logger.warning("Conquista ID "
                            + conquista.getId()
                            + " não encontrada.");

                    return new RuntimeException(
                            "Conquista não encontrada"
                    );
                });

        existing.setTitulo(conquista.getTitulo());
        existing.setRaridade(conquista.getRaridade());

        return repository.save(existing);
    }

    /**
     * Remove uma conquista
     */
    public void delete(Long id) {

        logger.info("Removendo conquista ID: " + id);

        Conquista existing = repository.findById(id)
                .orElseThrow(() -> {

                    logger.warning("Conquista ID "
                            + id
                            + " não encontrada.");

                    return new RuntimeException(
                            "Conquista não encontrada"
                    );
                });

        repository.delete(existing);
    }
}