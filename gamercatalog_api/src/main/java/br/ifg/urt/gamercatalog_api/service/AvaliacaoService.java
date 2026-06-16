package br.ifg.urt.gamercatalog_api.service;

import java.time.LocalDate;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.AvaliacaoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.AvaliacaoResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.AvaliacaoMapper;
import br.ifg.urt.gamercatalog_api.model.Avaliacao;
import br.ifg.urt.gamercatalog_api.repository.AvaliacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AvaliacaoService {

    private static final Logger logger = Logger.getLogger(AvaliacaoService.class.getName());

    private final AvaliacaoRepository repository;
    private final AvaliacaoMapper mapper;

    public AvaliacaoService(AvaliacaoRepository repository, AvaliacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AvaliacaoResponseDTO findById(Long id) {
        logger.info("Buscando avaliação no banco com ID: " + id);
        Avaliacao avaliacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação com ID " + id + " não foi encontrada."));
        return mapper.toResponseDTO(avaliacao);
    }

    public Page<AvaliacaoResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    public Page<AvaliacaoResponseDTO> findByJogo(Long jogoId, Pageable pageable) {
        return repository.findByJogoId(jogoId, pageable).map(mapper::toResponseDTO);
    }

    public AvaliacaoResponseDTO create(AvaliacaoRequestDTO dto) {
        logger.info("Salvando nova avaliação via DTO no banco.");

        if (dto.usuarioId() == null || dto.jogoId() == null) {
            throw new IllegalArgumentException("A avaliação deve possuir um usuário e um jogo válidos.");
        }

        Avaliacao avaliacao = mapper.toEntity(dto);
        avaliacao.setDataPostagem(LocalDate.now());

        Avaliacao salva = repository.save(avaliacao);
        return mapper.toResponseDTO(salva);
    }

    @Transactional
    public AvaliacaoResponseDTO update(Long id, AvaliacaoRequestDTO dto) {
        logger.info("Atualizando avaliação ID: " + id);

        Avaliacao existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível atualizar: Avaliação com ID " + id + " não existe."));

        existing.setNota(dto.nota());
        existing.setTextoCritica(dto.textoCritica());

        Avaliacao atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }

    public void delete(Long id) {
        logger.info("Removendo avaliação ID: " + id);
        Avaliacao existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Avaliação com ID " + id + " não existe."));
        repository.delete(existing);
    }
}