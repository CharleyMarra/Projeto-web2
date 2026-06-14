package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotNull;

public record FavoritosRequestDTO(
    @NotNull(message = "O ID do usuário é obrigatório")
    Long usuarioId,

    @NotNull(message = "O ID do jogo é obrigatório")
    Long jogoId
) {}