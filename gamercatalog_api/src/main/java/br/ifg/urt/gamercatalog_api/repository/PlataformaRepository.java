package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Plataforma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PlataformaRepository
        extends JpaRepository<Plataforma, Long> {

    /**
     * Busca uma plataforma por ID
     * ou lança exceção
     */
    default Plataforma findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Plataforma não encontrada com ID: " + id
                        )
                );
    }

    // Busca exata por nome
    Optional<Plataforma> findByNome(String nome);

    // Busca por parte do nome
    Page<Plataforma> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Busca por fabricante
    Page<Plataforma> findByFabricante(String fabricante, Pageable pageable);

    // Ordena por nome
    Page<Plataforma> findAllByOrderByNomeAsc(Pageable pageable);
}