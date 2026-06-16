package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Dlc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface DlcRepository extends JpaRepository<Dlc, Long> {

    // Buscar DLC pelo nome
    List<Dlc> findByNome(String nome);

    Page<Dlc> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Buscar DLCs com preço menor que o valor informado
    Page<Dlc> findByPrecoLessThan(Double preco, Pageable pageable);

    // Buscar DLCs vinculadas a um ID de jogo específico
    Page<Dlc> findByJogoId(Long jogoId, Pageable pageable);
}