package br.ifg.urt.gamercatalog_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifg.urt.gamercatalog_api.model.Dlc;

@Repository
public interface DlcRepository extends JpaRepository<Dlc, Long> {

    // Buscar DLC pelo nome
    List<Dlc> findByNome(String nome);

    // Buscar DLCs com preço menor que o valor informado
    List<Dlc> findByPrecoLessThan(Double preco);

    // ADICIONADO: Buscar DLCs vinculadas a um ID de jogo específico
    List<Dlc> findByJogoId(Long jogoId);
}