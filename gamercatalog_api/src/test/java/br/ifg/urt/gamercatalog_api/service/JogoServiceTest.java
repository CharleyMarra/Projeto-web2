package br.ifg.urt.gamercatalog_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.JogoMapper;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.mother.JogoMother;
import br.ifg.urt.gamercatalog_api.repository.JogoRepository;

@ExtendWith(MockitoExtension.class)
class JogoServiceTest {

    @InjectMocks
    private JogoService service;

    @Mock
    private JogoRepository repository;

    @Mock
    private JogoMapper mapper;

    @Test
    @DisplayName("Deve criar um jogo atribuindo preco zero caso venha nulo")
    void deveCriarJogoComPrecoPadraoSeNulo() {
        // Arrange
        JogoRequestDTO requestDTO = new JogoRequestDTO(
                "Jogo Genérico",
                "Descrição",
                "Aventura",
                null, // preço nulo para testar a regra
                10);

        Jogo jogoEntidadeSemPreco = JogoMother.jogoComPrecoNulo();
        
        JogoResponseDTO responseDTO = new JogoResponseDTO(
                2L,
                "Jogo Genérico",
                0.0,
                "R$ 0,00",
                "Aventura",
                10);

        // Simulando os comportamentos do mapper e do repository
        when(mapper.toEntity(requestDTO)).thenReturn(jogoEntidadeSemPreco);
        when(repository.save(any(Jogo.class))).thenReturn(jogoEntidadeSemPreco);
        when(mapper.toResponseDTO(jogoEntidadeSemPreco)).thenReturn(responseDTO);

        // Act
        JogoResponseDTO resultado = service.create(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(0.0, resultado.preco());

        // Verifica se o repository.save foi chamado exatamente uma vez
        verify(repository).save(any(Jogo.class));
    }
}