package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import br.ifg.urt.gamercatalog_api.dto.request.PublisherRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.PublisherMapper;
import br.ifg.urt.gamercatalog_api.model.Publisher;
import br.ifg.urt.gamercatalog_api.repository.PublisherRepository;

@Service
public class PublisherService {

    private static final Logger logger = Logger.getLogger(PublisherService.class.getName());
    private final PublisherRepository repository;
    private final PublisherMapper mapper;

    public PublisherService(PublisherRepository repository, PublisherMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "publishers", key = "#id")
    public PublisherResponseDTO findById(Long id) {
        logger.info("Buscando publisher no banco com ID: " + id);
        Publisher publisher = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher com ID " + id + " não foi encontrada."));
        return mapper.toResponseDTO(publisher);
    }

    @Cacheable(value = "publishersPaginados", key = "{ #nome, #pageable.pageNumber, #pageable.pageSize, #pageable.sort }")
    public Page<PublisherResponseDTO> findAll(String nome, Pageable pageable) {
        Page<Publisher> pagina;
        
        // Verifica se o filtro por nome foi enviado
        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        
        // Converte a página de Publisher para PublisherResponseDTO
        return pagina.map(mapper::toResponseDTO);
    }

    @CacheEvict(value = "publishersPaginados", allEntries = true)
    public PublisherResponseDTO create(PublisherRequestDTO dto) {
        logger.info("Salvando novo publisher via DTO no banco.");
        Publisher publisher = mapper.toEntity(dto);
        Publisher salvo = repository.save(publisher);
        return mapper.toResponseDTO(salvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "publishers", key = "#id"),
        @CacheEvict(value = "publishersPaginados", allEntries = true)
    })
    @Transactional
    public PublisherResponseDTO update(Long id, PublisherRequestDTO dto) {
        logger.info("Atualizando publisher ID: " + id);

        Publisher existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível atualizar: Publisher com ID " + id + " não existe."));

        existing.setNome(dto.nome());
        existing.setSede(dto.paisSede());

        Publisher atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    @Caching(evict = {
        @CacheEvict(value = "publishers", key = "#id"),
        @CacheEvict(value = "publishersPaginados", allEntries = true)
    })
    public void delete(Long id) {
        logger.info("Removendo publisher ID: " + id);
        Publisher existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Publisher com ID " + id + " não existe."));
        repository.delete(existing);
    }
}