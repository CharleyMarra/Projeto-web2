package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Plataforma;

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
    List<Plataforma> findByNomeContainingIgnoreCase(
            String nome
    );

    // Busca por fabricante
    List<Plataforma> findByFabricante(
            String fabricante
    );

    // Ordena por nome
    List<Plataforma> findAllByOrderByNomeAsc();
}