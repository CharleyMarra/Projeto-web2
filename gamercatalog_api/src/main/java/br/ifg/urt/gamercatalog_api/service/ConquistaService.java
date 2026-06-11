package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.ConquistaMapper;
import br.ifg.urt.gamercatalog_api.model.Conquista;
import br.ifg.urt.gamercatalog_api.repository.ConquistaRepository;

@Service
public class ConquistaService {

    private static final Logger logger = Logger.getLogger(ConquistaService.class.getName());

    private final ConquistaRepository repository;
    private final ConquistaMapper mapper;

    public ConquistaService(ConquistaRepository repository, ConquistaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ConquistaResponseDTO findById(Long id) {
        logger.info("Buscando conquista no banco com ID: " + id);
        Conquista conquista = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conquista não encontrada"));
        return mapper.toResponseDTO(conquista);
    }

    public List<ConquistaResponseDTO> findAll() {
        logger.info("Buscando todas as conquistas no banco.");
        return mapper.toResponseDTOList(repository.findAll());
    }

    public List<ConquistaResponseDTO> findByUsuario(Long usuarioId) {
        logger.info("Buscando conquistas para o usuário ID: " + usuarioId);
        return mapper.toResponseDTOList(repository.findByUsuarioId(usuarioId));
    }

    public ConquistaResponseDTO create(ConquistaRequestDTO dto) {
        logger.info("Salvando nova conquista via DTO no banco.");
        Conquista conquista = mapper.toEntity(dto);
        Conquista salva = repository.save(conquista);
        return mapper.toResponseDTO(salva);
    }

    @Transactional
    public ConquistaResponseDTO update(Long id, ConquistaRequestDTO dto) {
        logger.info("Atualizando conquista ID: " + id);

        Conquista existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conquista não encontrada"));

        existing.setTitulo(dto.titulo());
        existing.setRaridade(dto.raridade());

        Conquista atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }

    public void delete(Long id) {
        logger.info("Removendo conquista ID: " + id);
        Conquista existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conquista não encontrada"));
        repository.delete(existing);
    }
}