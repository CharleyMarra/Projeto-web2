package br.ifg.urt.gamercatalog_api.dto.request;

public record ComentariosRequestDTO(
        String texto,
        Long usuarioId,
        Long jogoId
) {
}