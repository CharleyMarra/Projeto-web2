package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.JogoMapper;
import br.ifg.urt.gamercatalog_api.model.Estudio;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.model.Plataforma;
import br.ifg.urt.gamercatalog_api.model.Publisher;
import br.ifg.urt.gamercatalog_api.repository.EstudioRepository;
import br.ifg.urt.gamercatalog_api.repository.JogoRepository;
import br.ifg.urt.gamercatalog_api.repository.PlataformaRepository;
import br.ifg.urt.gamercatalog_api.repository.PublisherRepository;

@Service
public class JogoService {

    private static final Logger logger = Logger.getLogger(JogoService.class.getName());
    
    private final JogoRepository repository;
    private final JogoMapper mapper;
    private final PlataformaRepository plataformaRepository;
    private final EstudioRepository estudioRepository;
    private final PublisherRepository publisherRepository;

    public JogoService(JogoRepository repository, JogoMapper mapper, 
                       PlataformaRepository plataformaRepository, 
                       EstudioRepository estudioRepository, 
                       PublisherRepository publisherRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.plataformaRepository = plataformaRepository;
        this.estudioRepository = estudioRepository;
        this.publisherRepository = publisherRepository;
    }

    @Cacheable(value = "jogos", key = "#id")
    @Transactional(readOnly = true)
    public JogoResponseDTO findById(Long id) {
        logger.info("Buscando jogo no banco com ID: " + id);
        Jogo jogo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo com ID " + id + " não foi encontrado."));
        return mapper.toResponseDTO(jogo);
    }

    @Cacheable(value = "jogosPaginados", key = "{ #nome, #pageable.pageNumber, #pageable.pageSize, #pageable.sort }")
    @Transactional(readOnly = true)
    public Page<JogoResponseDTO> findAll(String nome, Pageable pageable) {
        Page<Jogo> pagina;
        
        // Verifica se o filtro por nome foi enviado
        if (nome != null && !nome.isBlank()) {
            // CORREÇÃO: Trocado para chamar findByTituloContainingIgnoreCase
            pagina = repository.findByTituloContainingIgnoreCase(nome, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        
        return pagina.map(mapper::toResponseDTO);
    }

    @CacheEvict(value = "jogosPaginados", allEntries = true)
    @Transactional
    public JogoResponseDTO create(JogoRequestDTO dto) {
        logger.info("Salvando novo jogo via DTO no banco.");
        Jogo jogo = mapper.toEntity(dto);
        
        // Busca e vinculação das entidades relacionais
        Plataforma plataforma = plataformaRepository.findById(dto.plataformaId())
                .orElseThrow(() -> new ResourceNotFoundException("Plataforma não encontrada com o ID: " + dto.plataformaId()));
        
        Estudio estudio = estudioRepository.findById(dto.estudioId())
                .orElseThrow(() -> new ResourceNotFoundException("Estúdio não encontrado com o ID: " + dto.estudioId()));
                
        Publisher publisher = publisherRepository.findById(dto.publisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publicadora não encontrada com o ID: " + dto.publisherId()));

        jogo.setPlataforma(plataforma);
        jogo.setEstudio(estudio);
        jogo.setPublisher(publisher);

        Jogo salvo = repository.save(jogo);
        return mapper.toResponseDTO(salvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "jogos", key = "#id"),
        @CacheEvict(value = "jogosPaginados", allEntries = true)
    })
    @Transactional
    public JogoResponseDTO update(Long id, JogoRequestDTO dto) {
        logger.info("Atualizando jogo ID: " + id);

        Jogo existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível atualizar: Jogo com ID " + id + " não existe."));

        existing.setTitulo(dto.nome()); // Mantendo setTitulo devido à configuração anterior
        existing.setDescricao(dto.descricao());
        existing.setGenero(dto.genero());
        existing.alterarPreco(dto.preco());
        existing.setClassificacaoIndicativa(dto.classificacaoIndicativa());

        // Atualiza também os relacionamentos se mudarem
        Plataforma plataforma = plataformaRepository.findById(dto.plataformaId())
                .orElseThrow(() -> new ResourceNotFoundException("Plataforma não encontrada com o ID: " + dto.plataformaId()));
        Estudio estudio = estudioRepository.findById(dto.estudioId())
                .orElseThrow(() -> new ResourceNotFoundException("Estúdio não encontrado com o ID: " + dto.estudioId()));
        Publisher publisher = publisherRepository.findById(dto.publisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publicadora não encontrada com o ID: " + dto.publisherId()));

        existing.setPlataforma(plataforma);
        existing.setEstudio(estudio);
        existing.setPublisher(publisher);

        Jogo atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    @Caching(evict = {
        @CacheEvict(value = "jogos", key = "#id"),
        @CacheEvict(value = "jogosPaginados", allEntries = true)
    })
    @Transactional
    public void delete(Long id) {
        logger.info("Removendo jogo ID: " + id);
        Jogo existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Jogo com ID " + id + " não existe."));
        repository.delete(existing);
    }
}