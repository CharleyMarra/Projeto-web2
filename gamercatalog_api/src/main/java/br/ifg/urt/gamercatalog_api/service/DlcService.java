package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ifg.urt.gamercatalog_api.model.Dlc;
import br.ifg.urt.gamercatalog_api.repository.DlcRepository;

@Service
public class DlcService {

    private static final Logger logger = Logger.getLogger(DlcService.class.getName());
    private final DlcRepository repository;

    public DlcService(DlcRepository repository) {
        this.repository = repository;
    }

    public Dlc findById(Long id) {
        logger.info("Buscando DLC no banco com ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DLC não encontrada"));
    }

    public Page<Dlc> findAll(String nome, Pageable pageable) {
        logger.info("Buscando DLCs paginadas. Filtro: " + nome);

        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, pageable);
        }

        return repository.findAll(pageable);
    }

    public Dlc create(Dlc dlc) {
        logger.info("Salvando nova DLC: " + dlc.getNome());
        return repository.save(dlc);
    }

    @Transactional
    public Dlc update(Dlc dlc) {
        logger.info("Atualizando DLC ID: " + dlc.getId());
        Dlc existing = repository.findById(dlc.getId())
                .orElseThrow(() -> new RuntimeException("DLC não encontrada"));

        existing.setNome(dlc.getNome());
        existing.setPreco(dlc.getPreco());

        return repository.save(existing);
    }

    public void delete(Long id) {
        logger.info("Removendo DLC ID: " + id);
        Dlc existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DLC não encontrada"));
        repository.delete(existing);
    }
}