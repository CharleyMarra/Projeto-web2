package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.PublisherRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
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

    public PublisherResponseDTO findById(Long id) {
        logger.info("Buscando publisher no banco com ID: " + id);
        Publisher publisher = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher não encontrado"));
        return mapper.toResponseDTO(publisher);
    }

    public List<PublisherResponseDTO> findAll() {
        logger.info("Buscando todos os publishers no banco.");
        return mapper.toResponseDTOList(repository.findAll());
    }

    public PublisherResponseDTO create(PublisherRequestDTO dto) {
        logger.info("Salvando novo publisher via DTO no banco.");
        Publisher publisher = mapper.toEntity(dto);
        Publisher salvo = repository.save(publisher);
        return mapper.toResponseDTO(salvo);
    }

    @Transactional
    public PublisherResponseDTO update(Long id, PublisherRequestDTO dto) {
        logger.info("Atualizando publisher ID: " + id);

        Publisher existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher não encontrado"));

        existing.setNome(dto.nome());
        existing.setSede(dto.paisSede());

        Publisher atualizado = repository.save(existing);
        return mapper.toResponseDTO(atualizado);
    }

    public void delete(Long id) {
        logger.info("Removendo publisher ID: " + id);
        Publisher existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher não encontrado"));
        repository.delete(existing);
    }
}