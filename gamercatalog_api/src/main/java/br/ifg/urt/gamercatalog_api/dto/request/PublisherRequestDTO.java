package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PublisherRequestDTO(
    @NotBlank(message = "O nome da publisher é obrigatório")
    String nome,

    @NotBlank(message = "O país sede é obrigatório")
    String paisSede
) {}