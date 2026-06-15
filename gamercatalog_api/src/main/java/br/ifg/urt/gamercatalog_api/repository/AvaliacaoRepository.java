package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AvaliacaoRepository
        extends JpaRepository<Avaliacao, Long> {

    /**
     * Busca uma avaliação por ID
     * ou lança exceção
     */
    default Avaliacao findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Avaliação não encontrada com ID: "
                                        + id
                        )
                );
    }

    // Busca avaliações pela nota
    Page<Avaliacao> findByNota(Integer nota, Pageable pageable);

    // Busca avaliações acima de uma nota
    Page<Avaliacao> findByNotaGreaterThan(Integer nota, Pageable pageable);

    // Busca avaliações abaixo de uma nota
    Page<Avaliacao> findByNotaLessThan(Integer nota, Pageable pageable);

    // Busca avaliações contendo texto
    List<Avaliacao> findByTextoCriticaContainingIgnoreCase(
            String textoCritica
    );

    // Busca avaliações por usuário
    List<Avaliacao> findByUsuarioId(Long usuarioId);

    // Busca avaliações por jogo
    Page<Avaliacao> findByJogoId(Long jogoId, Pageable pageable);

    // Ordena avaliações por data
    List<Avaliacao> findAllByOrderByDataPostagemDesc();
}
