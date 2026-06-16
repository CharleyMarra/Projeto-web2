package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentariosRequestDTO(
    @NotBlank(message = "O texto do comentário é obrigatório")
    @Size(max = 255, message = "O comentário não pode exceder 255 caracteres")
    String texto,

    @NotNull(message = "O ID do usuário é obrigatório")
    Long usuarioId,

    @NotNull(message = "O ID do jogo é obrigatório")
    Long jogoId
) {}