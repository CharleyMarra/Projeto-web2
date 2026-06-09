package br.ifg.urt.gamercatalog_api.dto.request;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha
) {
}