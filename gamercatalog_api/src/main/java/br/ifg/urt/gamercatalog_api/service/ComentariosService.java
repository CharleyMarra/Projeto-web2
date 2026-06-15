package br.ifg.urt.gamercatalog_api.service;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.ComentariosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ComentariosResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.ComentariosMapper;
import br.ifg.urt.gamercatalog_api.model.Comentarios;
import br.ifg.urt.gamercatalog_api.repository.ComentariosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ComentariosService {

    private static final Logger logger = Logger.getLogger(ComentariosService.class.getName());

    private final ComentariosRepository repository;
    private final ComentariosMapper mapper; // INJETADO O MAPPER

    // Construtor atualizado recebendo o Mapper automático
    public ComentariosService(ComentariosRepository repository, ComentariosMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Busca um comentário por ID e retorna formatado em DTO
     */
    public ComentariosResponseDTO findById(Long id) {

        logger.info("Buscando comentário no banco com ID: " + id);

        Comentarios comentario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário com ID " + id + " não foi encontrado."));

        return mapper.toResponseDTO(comentario); // Conversão para DTO
    }

    /**
     * Busca todos os comentários e retorna a lista em DTO
     */
    public Page<ComentariosResponseDTO> findAll(String texto, Pageable pageable) {
        Page<Comentarios> pagina;
        if (texto != null && !texto.isBlank()) {
            pagina = repository.findByTextoContainingIgnoreCase(texto, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }
        return pagina.map(mapper::toResponseDTO);
    }

    /**
     * Busca os comentários vinculados a um jogo específico
     */
    public Page<ComentariosResponseDTO> findByJogo(Long jogoId, Pageable pageable) {
        return repository.findByJogoId(jogoId, pageable).map(mapper::toResponseDTO);
    }

    /**
     * Cria um novo comentário a partir de um RequestDTO
     */
    public ComentariosResponseDTO create(ComentariosRequestDTO dto) {

        logger.info("Salvando novo comentário via DTO no banco.");

        if (dto.usuarioId() == null || dto.jogoId() == null) {
            throw new IllegalArgumentException("O comentário deve possuir um usuário e um jogo válidos.");
        }

        // Converte o DTO de entrada para a Entidade que vai pro banco
        Comentarios comentario = mapper.toEntity(dto);

        // Aplica a regra de negócio de data automática
        comentario.setDataHora(LocalDateTime.now());

        Comentarios salvo = repository.save(comentario);

        return mapper.toResponseDTO(salvo); // Retorna a resposta limpa (ResponseDTO)
    }

    /**
     * Atualiza o texto de um comentário existente
     */
    @Transactional
    public ComentariosResponseDTO update(Long id, ComentariosRequestDTO dto) {

        logger.info("Atualizando comentário ID: " + id);

        Comentarios existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível atualizar: Comentário com ID " + id + " não existe."));

        // Aplica a regra de negócio de edição contida no Model
        existing.editarComentario(dto.texto());

        Comentarios atualizado = repository.save(existing);

        return mapper.toResponseDTO(atualizado);
    }

    /**
     * Remove um comentário
     */
    public void delete(Long id) {

        logger.info("Removendo comentário ID: " + id);
        Comentarios existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Comentário com ID " + id + " não existe."));

        repository.delete(existing);
    }
}