package br.ifg.urt.gamercatalog_api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.ifg.urt.gamercatalog_api.mother.JogoMother;

public class JogoTest {

    @Test
    @DisplayName("Deve alterar o preco do jogo com sucesso")
    void deveAlterarOPrecoDoJogo() {
        // Arrange
        Jogo jogo = JogoMother.counterStrike2();
        Double novoValor = 85.50;

        // Act
        jogo.alterarPreco(novoValor);

        // Assert
        assertNotNull(jogo.getPreco());
        assertEquals(85.50, jogo.getPreco().valor());
        assertEquals("BRL", jogo.getPreco().moeda());
    }
}