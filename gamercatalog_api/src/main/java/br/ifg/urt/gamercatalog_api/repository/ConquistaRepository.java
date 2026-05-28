package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifg.urt.gamercatalog_api.model.Conquista;

@Repository
public interface ConquistaRepository extends JpaRepository<Conquista, Long> {

    // Buscar por título
    List<Conquista> findByTitulo(String titulo);

    // Buscar por raridade
    List<Conquista> findByRaridade(String raridade);
}