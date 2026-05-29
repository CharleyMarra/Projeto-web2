package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Comentarios;

@Repository
public interface ComentariosRepository
        extends JpaRepository<Comentarios, Long> {

    /**
     * Busca um comentário por ID ou lança exceção
     */
    default Comentarios findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Comentário não encontrado com ID: " + id
                        )
                );
    }

    // Busca exata por texto
    Optional<Comentarios> findByTexto(String texto);

    // Busca por parte do texto
    List<Comentarios> findByTextoContainingIgnoreCase(
            String texto
    );

    // Busca comentários por usuário
    List<Comentarios> findByUsuarioId(Long usuarioId);

    // Busca comentários por jogo
    List<Comentarios> findByJogoId(Long jogoId);

    // Ordena comentários por data/hora
    List<Comentarios> findAllByOrderByDataHoraDesc();
}
