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

    /**
     * Busca um favorito por ID ou lança exceção
     */
    default Favoritos findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Favorito não encontrado com ID: " + id
                        )
                );
    }

    // Busca favoritos de um usuário
    List<Favoritos> findByUsuario(Usuario usuario);

    // Busca favoritos de um jogo
    List<Favoritos> findByJogo(Jogo jogo);

    // Busca favoritos por usuário e jogo
    Optional<Favoritos> findByUsuarioAndJogo(
            Usuario usuario,
            Jogo jogo
    );

    // Ordena favoritos pela data de adição
    List<Favoritos> findAllByOrderByDataAdicionadoAsc();
}
