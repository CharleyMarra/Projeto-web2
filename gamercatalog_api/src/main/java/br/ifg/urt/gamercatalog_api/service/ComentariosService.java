package br.ifg.urt.gamercatalog_api.service;

import br.ifg.urt.gamercatalog_api.model.Comentarios;
import br.ifg.urt.gamercatalog_api.mock.ComentariosMock;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ComentariosService {

    // Logger padrão do Java
    private static final Logger logger =
            Logger.getLogger(ComentariosService.class.getName());

    // Lista simulando banco de dados
    private final List<Comentarios> comentarios =
            ComentariosMock.createList();

    public Comentarios findById(Long id) {

        logger.info("Buscando comentário com ID: " + id);

        return comentarios.stream()
                .filter(c -> c.getIdComentario().equals(id))
                .findFirst()
                .orElseThrow(() -> {

                    logger.warning(
                            "Comentário ID " + id + " não encontrado."
                    );

                    return new RuntimeException(
                            "Comentário não encontrado"
                    );
                });
    }
}