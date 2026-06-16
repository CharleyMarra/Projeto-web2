package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import br.ifg.urt.gamercatalog_api.dto.request.EstudioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.EstudioResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.EstudioMapper;
import br.ifg.urt.gamercatalog_api.model.Estudio;
import br.ifg.urt.gamercatalog_api.repository.EstudioRepository;

@Service
public class EstudioService {

    private static final Logger logger = Logger.getLogger(EstudioService.class.getName());
    private final EstudioRepository repository;
    private final EstudioMapper mapper;

    public EstudioService(EstudioRepository repository, EstudioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "estudios", key = "#id")
    public EstudioResponseDTO findById(Long id) {
        logger.info("Buscando estúdio no banco com ID: " + id);
        Estudio estudio = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estúdio com ID " + id + " não foi encontrado."));
        return mapper.toResponseDTO(estudio);
    }

    @Cacheable(value = "estudiosPaginados", key = "{ #nome, #pageable.pageNumber, #pageable.pageSize, #pageable.sort }")
    public Page<EstudioResponseDTO> findAll(String nome, Pageable pageable) {
        Page<Estudio> pagina;
        
        // Verifica se o filtro por nome foi enviado
        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        
        return pagina.map(mapper::toResponseDTO);
    }

    @CacheEvict(value = "estudiosPaginados", allEntries = true)
    public EstudioResponseDTO create(EstudioRequestDTO dto) {
        logger.info("Salvando novo estúdio via DTO no banco.");
        Estudio estudio = mapper.toEntity(dto);
        Estudio salvo = repository.save(estudio);
        return mapper.toResponseDTO(salvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "estudios", key = "#id"),
        @CacheEvict(value = "estudiosPaginados", allEntries = true)
    })
    @Transactional
    public EstudioResponseDTO update(Long id, EstudioRequestDTO dto) {
        logger.info("Atualizando estúdio ID: " + id);

        Estudio existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível atualizar: Estúdio com ID " + id + " não existe."));

        existing.setNome(dto.nome());
        existing.setPais(dto.paisOrigem());

        Estudio atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    @Caching(evict = {
        @CacheEvict(value = "estudios", key = "#id"),
        @CacheEvict(value = "estudiosPaginados", allEntries = true)
    })
    public void delete(Long id) {
        logger.info("Removendo estúdio ID: " + id);
        Estudio existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Estúdio com ID " + id + " não existe."));
        repository.delete(existing);
    }
}