package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.EstudioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.EstudioResponseDTO;
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

    public EstudioResponseDTO findById(Long id) {
        logger.info("Buscando estúdio no banco com ID: " + id);
        Estudio estudio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estúdio não encontrado"));
        return mapper.toResponseDTO(estudio);
    }

    public List<EstudioResponseDTO> findAll() {
        logger.info("Buscando todos os estúdios no banco.");
        return mapper.toResponseDTOList(repository.findAll());
    }

    public EstudioResponseDTO create(EstudioRequestDTO dto) {
        logger.info("Salvando novo estúdio via DTO no banco.");
        Estudio estudio = mapper.toEntity(dto);
        Estudio salvo = repository.save(estudio);
        return mapper.toResponseDTO(salvo);
    }

    @Transactional
    public EstudioResponseDTO update(Long id, EstudioRequestDTO dto) {
        logger.info("Atualizando estúdio ID: " + id);

        Estudio existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estúdio não encontrado"));

        existing.setNome(dto.nome());
        existing.setPais(dto.paisOrigem()); // Conforme mapeamento no EstudioMapper

        Estudio atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    public void delete(Long id) {
        logger.info("Removendo estúdio ID: " + id);
        Estudio existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estúdio não encontrado"));
        repository.delete(existing);
    }
}