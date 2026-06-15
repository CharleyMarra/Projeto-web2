package br.ifg.urt.gamercatalog_api.service;

import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import br.ifg.urt.gamercatalog_api.dto.request.FavoritosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.FavoritosResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ResourceNotFoundException;
import br.ifg.urt.gamercatalog_api.mapper.FavoritosMapper;
import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.repository.FavoritosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class FavoritosService {

    private static final Logger logger = Logger.getLogger(FavoritosService.class.getName());

    private final FavoritosRepository repository;
    private final FavoritosMapper mapper;

    public FavoritosService(FavoritosRepository repository, FavoritosMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public FavoritosResponseDTO findById(Long id) {
        logger.info("Buscando favorito no banco com ID: " + id);
        Favoritos favorito = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorito com ID " + id + " não foi encontrado."));
        return mapper.toResponseDTO(favorito);
    }

    public Page<FavoritosResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    public Page<FavoritosResponseDTO> findByUsuario(Long usuarioId, Pageable pageable) {
        return repository.findByUsuarioId(usuarioId, pageable).map(mapper::toResponseDTO);
    }

    public FavoritosResponseDTO create(FavoritosRequestDTO dto) {
        logger.info("Executando regra de negócio para adicionar favorito.");

        if (dto.usuarioId() == null || dto.jogoId() == null) {
            throw new IllegalArgumentException("Usuário e Jogo são obrigatórios para favoritar.");
        }

        Favoritos favorito = mapper.toEntity(dto);

        Optional<Favoritos> existente = repository.findByUsuarioAndJogo(
                favorito.getUsuario(),
                favorito.getJogo());

        if (existente.isPresent()) {
            throw new IllegalStateException("Este jogo já está na lista de favoritos deste usuário.");
        }

        favorito.adicionarFavorito();
        Favoritos salvo = repository.save(favorito);
        return mapper.toResponseDTO(salvo);
    }

    public void delete(Long id) {
        logger.info("Removendo favorito ID: " + id);
        Favoritos existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível excluir: Favorito com ID " + id + " não existe."));
        repository.delete(existing);
    }
}