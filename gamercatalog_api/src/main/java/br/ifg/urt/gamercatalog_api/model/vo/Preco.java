package br.ifg.urt.gamercatalog_api.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable // Permite que o JPA grave os campos deste VO na mesma tabela do Jogo
public record Preco(
    @Column(name = "preco_valor", nullable = false)
    Double valor,

    @Column(name = "preco_moeda", length = 3)
    String moeda
) {
    // O VO se autovalida na criação
    public Preco {
        if (valor == null || valor < 0) {
            throw new IllegalArgumentException("O valor do preço não pode ser negativo");
        }
        if (moeda == null || moeda.isBlank()) {
            moeda = "BRL"; // Default
        }
    }

    // O VO pode ter lógica própria (ex: formatar para exibição)
    public String getFormatado() {
        return String.format("%s %.2f", moeda, valor);
    }
}