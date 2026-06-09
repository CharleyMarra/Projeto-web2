package br.ifg.urt.gamercatalog_api.dto.response;

public record JogoResponseDTO(
        Long id,
        String nome,
        String descricao,
        String genero
) {
}