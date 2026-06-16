package br.ifg.urt.gamercatalog_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.mapper.JogoMapper;
import br.ifg.urt.gamercatalog_api.model.Estudio;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.model.Plataforma;
import br.ifg.urt.gamercatalog_api.model.Publisher;
import br.ifg.urt.gamercatalog_api.repository.EstudioRepository;
import br.ifg.urt.gamercatalog_api.repository.JogoRepository;
import br.ifg.urt.gamercatalog_api.repository.PlataformaRepository;
import br.ifg.urt.gamercatalog_api.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
class JogoServiceTest {

    @InjectMocks
    private JogoService service;

    @Mock
    private JogoRepository repository;

    @Mock
    private JogoMapper mapper;

    // 1. Adicionados os novos mocks que o JogoService precisa
    @Mock
    private PlataformaRepository plataformaRepository;

    @Mock
    private EstudioRepository estudioRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Test
    @DisplayName("Deve criar um jogo associando as entidades corretamente")
    void deveCriarJogoComSucesso() {
        // Arrange
        // 2. Adicionados os IDs (1L) no RequestDTO
        JogoRequestDTO requestDTO = new JogoRequestDTO(
                "Jogo Genérico",
                "Descrição",
                "Aventura",
                0.0, 
                10,
                1L, 
                1L, 
                1L);

        Jogo jogoEntidade = new Jogo(); 
        Plataforma plataforma = new Plataforma();
        Estudio estudio = new Estudio();
        Publisher publisher = new Publisher();

        // 3. Adicionados os null no final do ResponseDTO
        JogoResponseDTO responseDTO = new JogoResponseDTO(
                2L,
                "Jogo Genérico",
                0.0,
                "R$ 0,00",
                "Aventura",
                10,
                null,
                null,
                null);

        // Simulando a conversão do mapper
        when(mapper.toEntity(requestDTO)).thenReturn(jogoEntidade);
        
        // 4. Simulando a busca no banco pelas entidades relacionadas
        when(plataformaRepository.findById(1L)).thenReturn(Optional.of(plataforma));
        when(estudioRepository.findById(1L)).thenReturn(Optional.of(estudio));
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        // Simulando o salvamento e retorno
        when(repository.save(any(Jogo.class))).thenReturn(jogoEntidade);
        when(mapper.toResponseDTO(jogoEntidade)).thenReturn(responseDTO);

        // Act
        JogoResponseDTO resultado = service.create(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Jogo Genérico", resultado.nome());

        // Verifica se todas as dependências foram chamadas corretamente
        verify(plataformaRepository).findById(1L);
        verify(estudioRepository).findById(1L);
        verify(publisherRepository).findById(1L);
        verify(repository).save(any(Jogo.class));
    }
}