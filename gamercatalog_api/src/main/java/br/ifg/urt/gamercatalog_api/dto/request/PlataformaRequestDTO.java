package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PlataformaRequestDTO(
    @NotBlank(message = "O nome da plataforma é obrigatório")
    String nome,

    @NotBlank(message = "O fabricante é obrigatório")
    String fabricante
) {}