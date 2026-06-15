package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.DlcRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.DlcResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.DlcMapper;
import br.ifg.urt.gamercatalog_api.model.Dlc;
import br.ifg.urt.gamercatalog_api.repository.DlcRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class DlcService {

    private static final Logger logger = Logger.getLogger(DlcService.class.getName());

    private final DlcRepository repository;
    private final DlcMapper mapper;

    public DlcService(DlcRepository repository, DlcMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public DlcResponseDTO findById(Long id) {
        logger.info("Buscando DLC no banco com ID: " + id);
        Dlc dlc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DLC não encontrada"));
        return mapper.toResponseDTO(dlc);
    }

    public Page<DlcResponseDTO> findAll(String nome, Pageable pageable) {
        Page<Dlc> pagina;
        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        return pagina.map(mapper::toResponseDTO);
    }

    public Page<DlcResponseDTO> findByJogo(Long jogoId, Pageable pageable) {
        return repository.findByJogoId(jogoId, pageable).map(mapper::toResponseDTO);
    }

    public DlcResponseDTO create(DlcRequestDTO dto) {
        logger.info("Salvando nova DLC via DTO no banco.");
        Dlc dlc = mapper.toEntity(dto);
        Dlc salva = repository.save(dlc);
        return mapper.toResponseDTO(salva);
    }

    @Transactional
    public DlcResponseDTO update(Long id, DlcRequestDTO dto) {
        logger.info("Atualizando DLC ID: " + id);

        Dlc existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DLC não encontrada"));

        existing.setNome(dto.nome());
        existing.setPreco(dto.preco());

        Dlc atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }

    public void delete(Long id) {
        logger.info("Removendo DLC ID: " + id);
        Dlc existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DLC não encontrada"));
        repository.delete(existing);
    }
}