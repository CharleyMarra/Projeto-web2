package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EstudioRequestDTO(
    @NotBlank(message = "O nome do estúdio é obrigatório")
    String nome,

    @NotBlank(message = "O país de origem é obrigatório")
    String paisOrigem
) {}