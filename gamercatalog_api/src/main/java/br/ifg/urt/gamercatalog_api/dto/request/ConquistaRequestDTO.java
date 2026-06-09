package br.ifg.urt.gamercatalog_api.dto.request;

public record ConquistaRequestDTO(
        String titulo,
        String raridade,
        Long jogoId
) {
}