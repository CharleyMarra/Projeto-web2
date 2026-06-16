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
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.repository.JogoRepository;

@Service
public class JogoService {

    private static final Logger logger = Logger.getLogger(JogoService.class.getName());
    private final JogoRepository repository;
    private final JogoMapper mapper;

    public JogoService(JogoRepository repository, JogoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "jogos", key = "#id")
    public JogoResponseDTO findById(Long id) {
        logger.info("Buscando jogo no banco com ID: " + id);
        Jogo jogo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo com ID " + id + " não foi encontrado no catálogo."));
        return mapper.toResponseDTO(jogo);
    }

    @Cacheable(value = "jogosPaginados", key = "{ #nome, #pageable.pageNumber, #pageable.pageSize, #pageable.sort }")
    public Page<JogoResponseDTO> findAll(String nome, Pageable pageable) { 
        Page<Jogo> pagina; 
        
        // Verifica se o usuário enviou algum nome para filtrar
        if (nome != null && !nome.isBlank()) { 
            pagina = repository.findByTituloContainingIgnoreCase(nome, pageable); 
        } else {
            pagina = repository.findAll(pageable); 
        }
        
        return pagina.map(mapper::toResponseDTO); 
    }

    @CacheEvict(value = "jogosPaginados", allEntries = true)
    public JogoResponseDTO create(JogoRequestDTO dto) {
        logger.info("Salvando novo jogo via DTO no banco.");
        Jogo jogo = mapper.toEntity(dto);
        
        if(jogo.getPreco() == null) {
            jogo.alterarPreco(0.0); 
        }
        if(jogo.getClassificacaoIndicativa() == null) {
            jogo.setClassificacaoIndicativa(0);
        }

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

        existing.setTitulo(dto.nome());
        existing.setDescricao(dto.descricao());
        existing.setGenero(dto.genero());

        if (dto.preco() != null) {
            existing.alterarPreco(dto.preco());
        }

        Jogo atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    @Caching(evict = {
        @CacheEvict(value = "jogos", key = "#id"),
        @CacheEvict(value = "jogosPaginados", allEntries = true)
    })
    public void delete(Long id) {
        logger.info("Removendo jogo ID: " + id);
        Jogo existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Jogo com ID " + id + " não existe."));
        repository.delete(existing);
    }
}