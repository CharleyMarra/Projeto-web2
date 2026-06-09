package br.ifg.urt.gamercatalog_api.dto.request;

public record FavoritosRequestDTO(
        Long usuarioId,
        Long jogoId
) {
}