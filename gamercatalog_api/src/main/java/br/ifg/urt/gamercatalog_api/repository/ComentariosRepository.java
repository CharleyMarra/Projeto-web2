package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Comentarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<Comentarios> findByTextoContainingIgnoreCase(String texto, Pageable pageable);

    // Busca comentários por usuário
    Page<Comentarios> findByUsuarioId(Long usuarioId, Pageable pageable);

    // Busca comentários por jogo
    Page<Comentarios> findByJogoId(Long jogoId, Pageable pageable);

    // Ordena comentários por data/hora
    Page<Comentarios> findAllByOrderByDataHoraDesc();
}
