package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.model.Usuario;

@Repository
public interface FavoritosRepository
        extends JpaRepository<Favoritos, Long> {

    default Favoritos findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado com ID: " + id));
    }

    List<Favoritos> findByUsuario(Usuario usuario);

    // ADICIONADO: Busca os favoritos de um usuário diretamente pelo ID numérico dele
    List<Favoritos> findByUsuarioId(Long usuarioId);

    List<Favoritos> findByJogo(Jogo jogo);

    Optional<Favoritos> findByUsuarioAndJogo(Usuario usuario, Jogo jogo);

    List<Favoritos> findAllByOrderByDataAdicionadoAsc();
}