package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.dto.request.PlataformaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.PlataformaMapper;
import br.ifg.urt.gamercatalog_api.model.Plataforma;
import br.ifg.urt.gamercatalog_api.repository.PlataformaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PlataformaService {

    private static final Logger logger = Logger.getLogger(PlataformaService.class.getName());

    private final PlataformaRepository repository;
    private final PlataformaMapper mapper;

    public PlataformaService(PlataformaRepository repository, PlataformaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PlataformaResponseDTO findById(Long id) {
        logger.info("Buscando plataforma no banco com ID: " + id);
        Plataforma plataforma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));
        return mapper.toResponseDTO(plataforma);
    }

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

    public PlataformaResponseDTO create(PlataformaRequestDTO dto) {
        logger.info("Salvando nova plataforma via DTO no banco.");
        Plataforma plataforma = mapper.toEntity(dto);
        Plataforma salva = repository.save(plataforma);
        return mapper.toResponseDTO(salva);
    }

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

    public void delete(Long id) {
        logger.info("Removendo plataforma ID: " + id);
        Plataforma existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));
        repository.delete(existing);
    }
}