package br.ifg.urt.gamercatalog_api.dto.response;

import java.time.LocalDateTime;

public record ComentariosResponseDTO(
        Long idComentario,
        String texto,
        LocalDateTime dataHora,
        String nomeUsuario, // Aglutina apenas o nome para exibição limpa
        String nomeJogo
) {
}