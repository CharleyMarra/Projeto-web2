package br.ifg.urt.gamercatalog_api.dto.request;

public record DlcRequestDTO(
        String nome,
        Double preco,
        Long jogoId
) {
}