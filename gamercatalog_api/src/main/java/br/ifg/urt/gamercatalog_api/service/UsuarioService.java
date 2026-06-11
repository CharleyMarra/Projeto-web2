package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.UsuarioResponseDTO;
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

    public UsuarioResponseDTO findById(Long id) {
        logger.info("Buscando usuário no banco com ID: " + id);
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return mapper.toResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> findAll() {
        logger.info("Buscando todos os usuários no banco.");
        return mapper.toResponseDTOList(repository.findAll());
    }

    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        logger.info("Salvando novo usuário via DTO no banco.");
        Usuario usuario = mapper.toEntity(dto);
        Usuario salvo = repository.save(usuario);
        return mapper.toResponseDTO(salvo);
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        logger.info("Atualizando usuário ID: " + id);

        Usuario existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existing.setNome(dto.nome());
        existing.setEmail(dto.email());
        existing.setSenha(dto.senha());

        Usuario atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    public void delete(Long id) {
        logger.info("Removendo usuário ID: " + id);
        Usuario existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repository.delete(existing);
    }
}