package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Avaliacao;

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
    List<Avaliacao> findByNota(Integer nota);

    // Busca avaliações acima de uma nota
    List<Avaliacao> findByNotaGreaterThan(Integer nota);

    // Busca avaliações abaixo de uma nota
    List<Avaliacao> findByNotaLessThan(Integer nota);

    // Busca avaliações contendo texto
    List<Avaliacao> findByTextoCriticaContainingIgnoreCase(
            String textoCritica
    );

    // Busca avaliações por usuário
    List<Avaliacao> findByUsuarioId(Long usuarioId);

    // Busca avaliações por jogo
    List<Avaliacao> findByJogoId(Long jogoId);

    // Ordena avaliações por data
    List<Avaliacao> findAllByOrderByDataPostagemDesc();
}