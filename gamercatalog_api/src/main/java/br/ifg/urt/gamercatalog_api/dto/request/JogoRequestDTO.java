package br.ifg.urt.gamercatalog_api.dto.request;

public record JogoRequestDTO(
        String nome,
        String descricao,
        String genero,
        Long estudioId,
        Long publisherId
) {
}