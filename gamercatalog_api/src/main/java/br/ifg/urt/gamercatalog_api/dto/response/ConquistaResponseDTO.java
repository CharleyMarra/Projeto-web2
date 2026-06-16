package br.ifg.urt.gamercatalog_api.dto.response;

public record ConquistaResponseDTO(
        Long id,
        String titulo,
        String raridade,
        String nomeJogo
) {
}