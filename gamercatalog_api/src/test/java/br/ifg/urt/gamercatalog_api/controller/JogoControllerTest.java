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
    private JogoModelAssembler assembler; // Essencial para o HATEOAS [cite: 2466]

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve adicionar um jogo com sucesso e retornar 201 Created")
    void deveAdicionarJogoComSucesso() throws Exception {
        // Arrange
        JogoRequestDTO requestDTO = new JogoRequestDTO(
                "Counter-Strike 2",
                "FPS Tático",
                "FPS",
                0.0,
                16);

        JogoResponseDTO responseDTO = new JogoResponseDTO(
                1L,
                "Counter-Strike 2",
                0.0,
                "R$ 0,00",
                "FPS",
                16);

        when(service.create(any(JogoRequestDTO.class))).thenReturn(responseDTO);

        // Configurando o comportamento simulado do Assembler [cite: 2480]
        when(assembler.toModel(responseDTO)).thenReturn(EntityModel.of(responseDTO));

        // Act & Assert
        mockMvc.perform(post("/jogos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))) // Converte o DTO para JSON [cite: 2484]
                .andExpect(status().isCreated()) // Valida o status 201 Created da sua API
                .andExpect(jsonPath("$.titulo").value("Counter-Strike 2")) // Navega e verifica o campo no JSON de
                                                                           // resposta [cite: 2485, 2496]
                .andExpect(jsonPath("$.genero").value("FPS"));

        // Verifica se o mock foi invocado corretamente
        verify(service).create(any(JogoRequestDTO.class));
    }
}