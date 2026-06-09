package br.ifg.urt.gamercatalog_api.dto.response;

import java.time.LocalDate;

public record AvaliacaoResponseDTO(
        Long idAvaliacao,
        Integer nota,
        String textoCritica,
        LocalDate dataPostagem,
        String nomeUsuario,
        String nomeJogo
) {
}