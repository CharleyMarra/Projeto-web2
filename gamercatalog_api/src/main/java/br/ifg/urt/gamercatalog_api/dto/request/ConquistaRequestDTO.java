package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConquistaRequestDTO(
    @NotBlank(message = "O título da conquista é obrigatório")
    @Size(min = 2, max = 100, message = "O título deve ter entre 2 e 100 caracteres")
    String titulo,

    @NotBlank(message = "A raridade é obrigatória")
    String raridade,

    @NotNull(message = "O ID do usuário é obrigatório")
    Long usuarioId
) {}