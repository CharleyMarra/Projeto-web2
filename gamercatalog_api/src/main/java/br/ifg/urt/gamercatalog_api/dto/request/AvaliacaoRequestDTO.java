package br.ifg.urt.gamercatalog_api.dto.request;

public record AvaliacaoRequestDTO(
        Integer nota,
        String textoCritica,
        Long usuarioId,
        Long jogoId
) {
}