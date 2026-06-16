package br.ifg.urt.gamercatalog_api.dto.response;

import java.time.LocalDateTime;

public record FavoritosResponseDTO(
        Long idFavorito,
        LocalDateTime dataAdicionado,
        String nomeUsuario,
        Long jogoId,
        String nomeJogo
) {
}