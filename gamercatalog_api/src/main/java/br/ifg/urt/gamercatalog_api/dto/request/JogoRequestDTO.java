package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record JogoRequestDTO(
    @NotBlank(message = "O nome do jogo é obrigatório")
    @Size(max = 150, message = "O nome do jogo não pode exceder 150 caracteres")
    String nome,

    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    String descricao,

    @NotBlank(message = "O gênero do jogo é obrigatório")
    String genero,

    @NotNull(message = "O preço é obrigatório")
    @PositiveOrZero(message = "O preço não pode ser negativo")
    Double preco,

    @NotNull(message = "A classificação indicativa é obrigatória")
    Integer classificacaoIndicativa
) {}