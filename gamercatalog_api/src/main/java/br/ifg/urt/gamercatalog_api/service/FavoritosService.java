package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import br.ifg.urt.gamercatalog_api.dto.request.FavoritosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.FavoritosResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.FavoritosMapper;
import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.repository.FavoritosRepository;

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
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));
        return mapper.toResponseDTO(favorito);
    }

    public List<FavoritosResponseDTO> findAll() {
        logger.info("Buscando todos os favoritos no banco.");
        return mapper.toResponseDTOList(repository.findAll());
    }

    public List<FavoritosResponseDTO> findByUsuario(Long usuarioId) {
        logger.info("Buscando favoritos do usuário ID: " + usuarioId);
        return mapper.toResponseDTOList(repository.findByUsuarioId(usuarioId));
    }

    public FavoritosResponseDTO create(FavoritosRequestDTO dto) {
        logger.info("Executando regra de negócio para adicionar favorito.");

        if (dto.usuarioId() == null || dto.jogoId() == null) {
            throw new IllegalArgumentException("Usuário e Jogo são obrigatórios para favoritar.");
        }

        Favoritos favorito = mapper.toEntity(dto);

        Optional<Favoritos> existente = repository.findByUsuarioAndJogo(
                favorito.getUsuario(),
                favorito.getJogo()
        );

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
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));
        repository.delete(existing);
    }
}