package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AvaliacaoRequestDTO(
    @NotNull(message = "A nota é obrigatória")
    @Min(value = 0, message = "A nota mínima é 0")
    @Max(value = 10, message = "A nota máxima é 10")
    Integer nota,

    @NotBlank(message = "O texto da crítica não pode estar vazio")
    @Size(max = 500, message = "A crítica não pode exceder 500 caracteres")
    String textoCritica,

    @NotNull(message = "O ID do usuário é obrigatório")
    Long usuarioId,

    @NotNull(message = "O ID do jogo é obrigatório")
    Long jogoId
) {}