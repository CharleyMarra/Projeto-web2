package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface JogoRepository
        extends JpaRepository<Jogo, Long> {

    /**
     * Busca um jogo por ID ou lança exceção
     */
    default Jogo findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Jogo não encontrado com ID: " + id
                        )
                );
    }

    // Busca exata por título
    Optional<Jogo> findByTitulo(String titulo);

    // Busca por parte do título
    Page<Jogo> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    // Busca por gênero
    Page<Jogo> findByGenero(String genero, Pageable pageable);

    // Busca jogos abaixo de determinado preço
    Page<Jogo> findByPrecoLessThan(Double preco, Pageable pageable);

    // Ordena por título
    Page<Jogo> findAllByOrderByTituloAsc();
}
