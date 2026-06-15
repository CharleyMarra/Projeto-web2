package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import br.ifg.urt.gamercatalog_api.dto.request.PlataformaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.PlataformaMapper;
import br.ifg.urt.gamercatalog_api.model.Plataforma;
import br.ifg.urt.gamercatalog_api.repository.PlataformaRepository;

@Service
public class PlataformaService {

    private static final Logger logger = Logger.getLogger(PlataformaService.class.getName());
    private final PlataformaRepository repository;
    private final PlataformaMapper mapper;

    public PlataformaService(PlataformaRepository repository, PlataformaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "plataformas", key = "#id")
    public PlataformaResponseDTO findById(Long id) {
        logger.info("Buscando plataforma no banco com ID: " + id);
        Plataforma plataforma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));
        return mapper.toResponseDTO(plataforma);
    }

    @Cacheable(value = "plataformasPaginadas", key = "{ #nome, #pageable.pageNumber, #pageable.pageSize, #pageable.sort }")
    public Page<PlataformaResponseDTO> findAll(String nome, Pageable pageable) {
        Page<Plataforma> pagina;
        
        // Verifica se o filtro por nome foi enviado
        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        
        // Converte a página de Plataforma para PlataformaResponseDTO
        return pagina.map(mapper::toResponseDTO);
    }

    @CacheEvict(value = "plataformasPaginadas", allEntries = true)
    public PlataformaResponseDTO create(PlataformaRequestDTO dto) {
        logger.info("Salvando nova plataforma via DTO no banco.");
        Plataforma plataforma = mapper.toEntity(dto);
        Plataforma salva = repository.save(plataforma);
        return mapper.toResponseDTO(salva);
    }

    @Caching(evict = {
        @CacheEvict(value = "plataformas", key = "#id"),
        @CacheEvict(value = "plataformasPaginadas", allEntries = true)
    })
    @Transactional
    public PlataformaResponseDTO update(Long id, PlataformaRequestDTO dto) {
        logger.info("Atualizando plataforma ID: " + id);

        Plataforma existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));

        existing.setNome(dto.nome());
        existing.setFabricante(dto.fabricante());

        Plataforma atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }

    @Caching(evict = {
        @CacheEvict(value = "plataformas", key = "#id"),
        @CacheEvict(value = "plataformasPaginadas", allEntries = true)
    })
    public void delete(Long id) {
        logger.info("Removendo plataforma ID: " + id);
        Plataforma existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));
        repository.delete(existing);
    }
}