package br.ifg.urt.gamercatalog_api.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.repository.FavoritosRepository;

@Service
public class FavoritosService {

    private static final Logger logger =
            Logger.getLogger(FavoritosService.class.getName());

    private final FavoritosRepository repository;

    public FavoritosService(FavoritosRepository repository) {
        this.repository = repository;
    }

    public Favoritos findById(Long id) {
        logger.info("Buscando favorito no banco com ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));
    }

    public List<Favoritos> findAll() {
        logger.info("Buscando todos os favoritos no banco.");
        return repository.findAll();
    }

    /**
     * REGRA DE NEGÓCIO: Busca os favoritos associados a um usuário específico
     */
    public List<Favoritos> findByUsuario(Long usuarioId) {
        logger.info("Buscando favoritos do usuário ID: " + usuarioId);
        return repository.findByUsuarioId(usuarioId);
    }

    /**
     * REGRA DE NEGÓCIO (RF08): Cria um favorito garantindo que não haja duplicatas
     */
    public Favoritos create(Favoritos favorito) {
        logger.info("Executando regra de negócio para adicionar favorito.");

        if (favorito.getUsuario() == null || favorito.getJogo() == null) {
            throw new IllegalArgumentException("Usuário e Jogo são obrigatórios para favoritar.");
        }

        Optional<Favoritos> existente = repository.findByUsuarioAndJogo(
                favorito.getUsuario(),
                favorito.getJogo()
        );

        if (existente.isPresent()) {
            throw new IllegalStateException("Este jogo já está na lista de favoritos deste usuário.");
        }

        favorito.adicionarFavorito();
        return repository.save(favorito);
    }

    @Transactional
    public Favoritos update(Favoritos favorito) {
        logger.info("Atualizando favorito ID: " + favorito.getIdFavorito());
        Favoritos existing = repository.findById(favorito.getIdFavorito())
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        existing.setDataAdicionado(favorito.getDataAdicionado());
        existing.setUsuario(favorito.getUsuario());
        existing.setJogo(favorito.getJogo());

        return repository.save(existing);
    }

    public void delete(Long id) {
        logger.info("Removendo favorito ID: " + id);
        Favoritos existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));
        repository.delete(existing);
    }
}