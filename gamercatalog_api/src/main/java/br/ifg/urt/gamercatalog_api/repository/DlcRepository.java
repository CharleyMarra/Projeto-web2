package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Dlc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface DlcRepository
        extends JpaRepository<Dlc, Long> {

    /**
     * Busca uma DLC por ID ou lança exceção
     */
    default Dlc findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "DLC não encontrada com ID: " + id
                        )
                );
    }

    // Busca exata por nome
    Optional<Dlc> findByNome(String nome);

    // Busca por parte do nome (Usado para o filtro do Controller)
    Page<Dlc> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Busca DLCs abaixo de determinado preço
    Page<Dlc> findByPrecoLessThan(Double preco, Pageable pageable);

    // Ordena por nome
    Page<Dlc> findAllByOrderByNomeAsc();
}