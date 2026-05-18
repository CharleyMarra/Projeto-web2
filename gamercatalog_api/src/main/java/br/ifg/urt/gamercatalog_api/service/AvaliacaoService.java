package br.ifg.urt.gamercatalog_api.service;

import br.ifg.urt.gamercatalog_api.model.Avaliacao;
import br.ifg.urt.gamercatalog_api.mock.AvaliacaoMock;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AvaliacaoService {

    // Logger padrão do Java
    private static final Logger logger =
            Logger.getLogger(AvaliacaoService.class.getName());

    // Lista simulando banco de dados
    private final List<Avaliacao> avaliacoes =
            AvaliacaoMock.createList();

    public Avaliacao findById(Long id) {

        logger.info("Buscando avaliação com ID: " + id);

        return avaliacoes.stream()
                .filter(a -> a.getIdAvaliacao().equals(id))
                .findFirst()
                .orElseThrow(() -> {

                    logger.warning(
                            "Avaliação ID " + id + " não encontrada."
                    );

                    return new RuntimeException(
                            "Avaliação não encontrada"
                    );
                });
    }
}