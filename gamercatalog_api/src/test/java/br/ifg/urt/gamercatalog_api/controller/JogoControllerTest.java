package br.ifg.urt.gamercatalog_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ifg.urt.gamercatalog_api.assembler.JogoModelAssembler;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.service.JogoService;

@WebMvcTest(JogoController.class)
class JogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JogoService service;

    @MockitoBean
    private JogoModelAssembler assembler; 

    // Mock obrigatório pois o JogoController agora depende do PagedResourcesAssembler para paginação
    @MockitoBean
    private PagedResourcesAssembler<JogoResponseDTO> pagedAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve adicionar um jogo com sucesso e retornar 201 Created")
    void deveAdicionarJogoComSucesso() throws Exception {
        // Arrange
        // Adicionados os IDs 1L para simular os relacionamentos (plataformaId, estudioId, publisherId)
        JogoRequestDTO requestDTO = new JogoRequestDTO(
                "Counter-Strike 2",
                "FPS Tático",
                "FPS",
                0.0,
                16,
                1L, 
                1L, 
                1L);

        // Adicionados "null" no final para representar plataforma, estudio e publisher na resposta simulada
        JogoResponseDTO responseDTO = new JogoResponseDTO(
                1L,
                "Counter-Strike 2",
                0.0,
                "BRL 0,00",
                "FPS",
                16,
                null,
                null,
                null);

        // Ajuste: o método do service agora se chama "create" (em inglês) conforme a sua última modificação
        when(service.create(any(JogoRequestDTO.class))).thenReturn(responseDTO);

        when(assembler.toModel(responseDTO)).thenReturn(EntityModel.of(responseDTO));

        // Act & Assert
        mockMvc.perform(post("/jogos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))) 
                .andExpect(status().isCreated()) 
                // Ajuste: o DTO de resposta possui o campo 'nome' e não 'titulo'
                .andExpect(jsonPath("$.nome").value("Counter-Strike 2")) 
                .andExpect(jsonPath("$.genero").value("FPS"));

        verify(service).create(any(JogoRequestDTO.class));
    }
}