package br.ifg.urt.gamercatalog_api.dto.response;

public record PublisherResponseDTO(
        Long id,
        String nome,
        String paisSede
) {
}