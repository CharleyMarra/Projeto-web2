package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Conquista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ConquistaRepository
        extends JpaRepository<Conquista, Long> {

    /**
     * Busca uma conquista por ID ou lança exceção
     */
    default Conquista findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Conquista não encontrada com ID: " + id
                        )
                );
    }

    // Busca exata por título
    Optional<Conquista> findByTitulo(String titulo);

    // Busca por parte do título (Usado para o filtro do Controller)
    Page<Conquista> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    // Busca por raridade
    Page<Conquista> findByRaridade(String raridade, Pageable pageable);

    // Ordena por título
    Page<Conquista> findAllByOrderByTituloAsc();
}