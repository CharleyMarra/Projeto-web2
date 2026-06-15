package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.ConquistaMapper;
import br.ifg.urt.gamercatalog_api.model.Conquista;
import br.ifg.urt.gamercatalog_api.repository.ConquistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
                .orElseThrow(() -> new ResourceNotFoundException("Conquista com ID " + id + " não foi encontrada."));
        return mapper.toResponseDTO(conquista);
    }

    public Page<ConquistaResponseDTO> findAll(String titulo, Pageable pageable) {
        Page<Conquista> pagina;
        if (titulo != null && !titulo.isBlank()) {
            pagina = repository.findByTituloContainingIgnoreCase(titulo, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        return pagina.map(mapper::toResponseDTO);
    }

    public Page<ConquistaResponseDTO> findByUsuario(Long usuarioId, Pageable pageable) {
        return repository.findByUsuarioId(usuarioId, pageable).map(mapper::toResponseDTO);
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
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível atualizar: Conquista com ID " + id + " não existe."));

        existing.setTitulo(dto.titulo());
        existing.setRaridade(dto.raridade());

        Conquista atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }

    public void delete(Long id) {
        logger.info("Removendo conquista ID: " + id);
        Conquista existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Conquista com ID " + id + " não existe."));
        repository.delete(existing);
    }
}