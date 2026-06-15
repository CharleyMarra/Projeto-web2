package br.ifg.urt.gamercatalog_api.mother;

import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.model.vo.Preco;

public class JogoMother {

    public static Jogo counterStrike2() {
        return new Jogo(
            1L, 
            "Counter-Strike 2", 
            "Jogo de tiro tático competitivo", 
            new Preco(0.0, "BRL"), 
            "FPS", 
            16
        );
    }

    public static Jogo jogoComPrecoNulo() {
        return new Jogo(
            2L, 
            "Jogo Genérico", 
            "Descrição genérica", 
            null, 
            "Aventura", 
            10
        );
    }
}