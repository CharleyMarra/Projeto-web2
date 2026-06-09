package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ifg.urt.gamercatalog_api.model.Conquista;

@Repository
public interface ConquistaRepository extends JpaRepository<Conquista, Long> {

    // Buscar por título
    List<Conquista> findByTitulo(String titulo);

    // Buscar por raridade
    List<Conquista> findByRaridade(String raridade);

    /**
     * Busca conquistas que pertencem aos jogos favoritados/comentados pelo usuário 
     * ou diretamente vinculadas caso exista histórico (Custom Query via JPQL)
     */
    @Query("SELECT c FROM Conquista c WHERE c.jogo.id IN (SELECT f.jogo.id FROM Favoritos f WHERE f.usuario.id = :usuarioId)")
    List<Conquista> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}