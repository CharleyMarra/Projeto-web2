package br.ifg.urt.gamercatalog_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuário por ID ou lança exceção
     */
    default Usuario findByIdOrThrow(Long id) {

        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado com ID: " + id
                        )
                );
    }

    // Busca por email (login)
    Optional<Usuario> findByEmail(String email);

    // Busca por nome (exato)
    Optional<Usuario> findByNome(String nome);

    // Busca por parte do nome
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}