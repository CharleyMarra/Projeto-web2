package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifg.urt.gamercatalog_api.model.Jogo;

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
    List<Jogo> findByTituloContainingIgnoreCase(
            String titulo
    );

    // Busca por gênero
    List<Jogo> findByGenero(String genero);

    // Busca jogos abaixo de determinado preço
    List<Jogo> findByPrecoLessThan(Double preco);

    // Ordena por título
    List<Jogo> findAllByOrderByTituloAsc();
}
