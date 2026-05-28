package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Publisher;

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
    List<Publisher> findByNomeContainingIgnoreCase(
            String nome
    );

    // Busca por sede
    List<Publisher> findBySede(
            String sede
    );

    // Ordena por nome
    List<Publisher> findAllByOrderByNomeAsc();
}