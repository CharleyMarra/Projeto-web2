package br.ifg.urt.gamercatalog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record JogoRequestDTO(
    @NotBlank(message = "O nome do jogo é obrigatório")
    String nome,
    
    String descricao,
    
    @NotBlank(message = "O gênero do jogo é obrigatório")
    String genero,
    
    @NotNull(message = "O preço é obrigatório")
    @PositiveOrZero(message = "O preço não pode ser negativo")
    Double preco,
    
    @NotNull(message = "A classificação indicativa é obrigatória")
    Integer classificacaoIndicativa,

    @NotNull(message = "O ID da plataforma é obrigatório")
    Long plataformaId,

    @NotNull(message = "O ID do estúdio é obrigatório")
    Long estudioId,

    @NotNull(message = "O ID da publicadora é obrigatório")
    Long publisherId
) {}