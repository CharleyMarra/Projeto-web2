package br.ifg.urt.gamercatalog_api.service;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import br.ifg.urt.gamercatalog_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.UsuarioMapper;
import br.ifg.urt.gamercatalog_api.model.Usuario;
import br.ifg.urt.gamercatalog_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "usuarios", key = "#id")
    public UsuarioResponseDTO findById(Long id) {
        logger.info("Buscando usuário no banco com ID: " + id);
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não cadastrado."));
        return mapper.toResponseDTO(usuario);
    }

    @Cacheable(value = "usuariosPaginados", key = "{ #nome, #pageable.pageNumber, #pageable.pageSize, #pageable.sort }")
    public Page<UsuarioResponseDTO> findAll(String nome, Pageable pageable) {
        Page<Usuario> pagina;
        
        // Filtra se o nome for informado
        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        
        return pagina.map(mapper::toResponseDTO);
    }

    @CacheEvict(value = "usuariosPaginados", allEntries = true)
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        logger.info("Salvando novo usuário via DTO no banco.");
        Usuario usuario = mapper.toEntity(dto);
        Usuario salvo = repository.save(usuario);
        return mapper.toResponseDTO(salvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "usuarios", key = "#id"),
        @CacheEvict(value = "usuariosPaginados", allEntries = true)
    })
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        logger.info("Atualizando usuário ID: " + id);

        Usuario existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível atualizar: Usuário com ID " + id + " não encontrado."));

        existing.setNome(dto.nome());
        existing.setEmail(dto.email());
        existing.setSenha(dto.senha());

        Usuario atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    @Caching(evict = {
        @CacheEvict(value = "usuarios", key = "#id"),
        @CacheEvict(value = "usuariosPaginados", allEntries = true)
    })
    public void delete(Long id) {
        logger.info("Removendo usuário ID: " + id);
        Usuario existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Usuário com ID " + id + " não encontrado."));
        repository.delete(existing);
    }
}