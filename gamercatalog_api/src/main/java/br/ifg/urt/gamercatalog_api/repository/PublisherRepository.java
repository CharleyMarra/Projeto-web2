package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PublisherRepository
        extends JpaRepository<Publisher, Long> {

    /**
     * Busca um publisher por ID
     * ou lança exceção
     */
    default Publisher findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Publisher não encontrado com ID: " + id
                        )
                );
    }

    // Busca exata por nome
    Optional<Publisher> findByNome(String nome);

    // Busca por parte do nome
    Page<Publisher> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Busca por sede
    Page<Publisher> findBySede(String sede, Pageable pageable);

    // Ordena por nome
    Page<Publisher> findAllByOrderByNomeAsc(Pageable pageable);
}