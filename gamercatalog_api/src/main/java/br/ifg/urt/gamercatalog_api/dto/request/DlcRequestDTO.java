package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record DlcRequestDTO(
    @NotBlank(message = "O nome da DLC é obrigatório")
    String nome,

    @NotNull(message = "O preço da DLC é obrigatório")
    @PositiveOrZero(message = "O preço da DLC não pode ser negativo")
    Double preco,

    @NotNull(message = "O ID do jogo é obrigatório")
    Long jogoId
) {}