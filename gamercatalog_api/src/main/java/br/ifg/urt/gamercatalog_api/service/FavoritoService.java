package br.ifg.urt.gamercatalog_api.service;

import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.mock.FavoritosMock;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class FavoritosService {

    // Logger padrão do Java
    private static final Logger logger =
            Logger.getLogger(FavoritosService.class.getName());

    // Lista simulando banco de dados
    private final List<Favoritos> favoritos =
            FavoritosMock.createList();

    public Favoritos findById(Long id) {

        logger.info("Buscando favorito com ID: " + id);

        return favoritos.stream()
                .filter(f -> f.getIdFavorito().equals(id))
                .findFirst()
                .orElseThrow(() -> {

                    logger.warning(
                            "Favorito ID " + id + " não encontrado."
                    );

                    return new RuntimeException(
                            "Favorito não encontrado"
                    );
                });
    }
}