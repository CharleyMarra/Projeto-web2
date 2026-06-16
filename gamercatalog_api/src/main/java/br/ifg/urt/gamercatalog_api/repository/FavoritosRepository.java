package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface FavoritosRepository
        extends JpaRepository<Favoritos, Long> {

    default Favoritos findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado com ID: " + id));
    }

    Page<Favoritos> findByUsuario(Usuario usuario, Pageable pageable);

    // Busca os favoritos de um usuário diretamente pelo ID numérico dele
    Page<Favoritos> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<Favoritos> findByJogo(Jogo jogo, Pageable pageable);

    Optional<Favoritos> findByUsuarioAndJogo(Usuario usuario, Jogo jogo);

    Page<Favoritos> findAllByOrderByDataAdicionadoAsc(Pageable pageable);
}