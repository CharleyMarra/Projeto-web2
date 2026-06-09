package br.ifg.urt.gamercatalog_api.dto.response;

public record DlcResponseDTO(
        Long id,
        String nome,
        Double preco,
        String nomeJogo
) {
}