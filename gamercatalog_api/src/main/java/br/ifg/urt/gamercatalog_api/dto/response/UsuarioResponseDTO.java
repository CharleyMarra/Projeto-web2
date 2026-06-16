package br.ifg.urt.gamercatalog_api.dto.response;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email
) {
}