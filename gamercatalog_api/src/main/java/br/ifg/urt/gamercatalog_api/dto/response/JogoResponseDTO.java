package br.ifg.urt.gamercatalog_api.dto.response;

public record JogoResponseDTO(
    Long id,
    String nome,
    Double preco,
    String precoFormatado,
    String genero,
    Integer classificacaoIndicativa,
    PlataformaResponseDTO plataforma,
    EstudioResponseDTO estudio,
    PublisherResponseDTO publisher
) {}