package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifg.urt.gamercatalog_api.model.Estudio;

@Repository
public interface EstudioRepository
        extends JpaRepository<Estudio, Long> {

    /**
     * Busca um estúdio por ID
     * ou lança exceção
     */
    default Estudio findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Estúdio não encontrado com ID: " + id
                        )
                );
    }

    // Busca exata por nome
    Optional<Estudio> findByNome(String nome);

    // Busca por parte do nome
    List<Estudio> findByNomeContainingIgnoreCase(
            String nome
    );

    // Busca por país
    List<Estudio> findByPais(String pais);

    // Ordena por nome
    List<Estudio> findAllByOrderByNomeAsc();
}
